package com.elihart.flickr;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BrowseActivity extends Activity {
	/** Use this fragment to retain state on config changes */
	private RetainedFragment mRetainedFragment;
	/**
	 * Tag to use when adding the retained fragment to the fragment manager
	 */
	private static final String RETAINED_FRAGMENT_TAG = "retainedFragment";

	/** Progress bar for loading initial image list. */
	private ProgressBar mProgress;
	/** Text for showing errors. */
	private TextView mText;
	/** Client for making flickr api queries. */
	private FlickrClient mFlickrClient;

	/** The photos from the query. */
	private List<FlickrPhoto> mPhotos;
	/** The fragment to show the grid of photos. */
	private GridFragment mGridFragment;
	/** The fragment to show the enlarged view of a photoo */
	private DetailFragment mDetailFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);

		// show progress while initializing app
		mProgress = (ProgressBar) findViewById(R.id.activity_progress);
		mText = (TextView) findViewById(R.id.text);

		FragmentManager fm = getFragmentManager();
		mGridFragment = (GridFragment) fm.findFragmentById(R.id.grid_fragment);
		mDetailFragment = (DetailFragment) fm
				.findFragmentById(R.id.detail_fragment);

		// find the retained fragment on activity restart
		mRetainedFragment = (RetainedFragment) fm
				.findFragmentByTag(RETAINED_FRAGMENT_TAG);

		// create the fragment and data the first time
		if (mRetainedFragment == null) {
			// add the fragment
			mRetainedFragment = new RetainedFragment();
			fm.beginTransaction().add(mRetainedFragment, RETAINED_FRAGMENT_TAG)
					.commit();

			mFlickrClient = new FlickrClient();

			loadInterestingPhotos();
		}
		// restore previous state
		else {
			mFlickrClient = mRetainedFragment.client;
			mPhotos = mRetainedFragment.photos;

			/* Start loading photos if it hasn't been done yet. */
			if (mPhotos == null) {
				loadInterestingPhotos();
			}
			/*
			 * If we already have the photos then we are resuming from a config
			 * change. Make sure a fragment is being shown.
			 */
			else if (mGridFragment.isHidden() && mDetailFragment.isHidden()) {
				fm.beginTransaction().show(mGridFragment).commit();
			}
		}

	}

	/**
	 * Do an async load of interesting flickr photos.
	 * 
	 */
	private void loadInterestingPhotos() {
		// hide fragments
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().hide(mDetailFragment).hide(mGridFragment)
				.commit();

		mText.setVisibility(View.GONE);
		mProgress.setVisibility(View.VISIBLE);

		// Start async query to get photo list
		mFlickrClient.getInterestingPhotos(new Callback<FlickrResponse>() {

			@Override
			public void success(FlickrResponse arg0, Response arg1) {
				handleSuccess(arg0);
			}

			@Override
			public void failure(RetrofitError arg0) {
				handleFailure(arg0);
			}
		});
	}

	/**
	 * Handle image loading failure.
	 * 
	 * @param arg0
	 */
	private void handleFailure(RetrofitError arg0) {
		mProgress.setVisibility(View.GONE);
		System.out.println(arg0.toString());
		mText.setText("Error loading image list");
		mText.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Handle image loading success. Load image objects into image grid.
	 * 
	 * @param arg0
	 */
	private void handleSuccess(FlickrResponse response) {
		mText.setVisibility(View.GONE);
		mProgress.setVisibility(View.GONE);
		mPhotos = response.getPhotos();

		mGridFragment.showPhotos(mPhotos);

		if (!isChangingConfigurations()) {
			FragmentManager fm = getFragmentManager();
			fm.beginTransaction()

			.show(mGridFragment).hide(mDetailFragment).commit();
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		/* Save instance state with fragment. */
		mRetainedFragment.client = mFlickrClient;
		mRetainedFragment.photos = mPhotos;
	}

	/**
	 * Fragment used to save activity state on config change.
	 */
	public static class RetainedFragment extends Fragment {

		public FlickrClient client;
		public List<FlickrPhoto> photos;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// retain this fragment
			setRetainInstance(true);
		}
	}

	/**
	 * Called when a photo in the grid is clicked.
	 * 
	 * @param photo
	 */
	public void photoClicked(FlickrPhoto photo) {
		mDetailFragment.showPhoto(photo);

		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().hide(mGridFragment).show(mDetailFragment)
				.addToBackStack("photo detail").commit();

	}

}
