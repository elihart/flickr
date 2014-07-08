package com.elihart.flickr;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BrowseActivity extends Activity {
	/** Progress bar for loading initial image list. */
	private ProgressBar mProgress;
	/** Text for showing errors. */
	private TextView mText;
	private FlickrClient mFlickrClient;
	/** The photos from the query. */
	private List<FlickrPhoto> mPhotos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);

		// show progress while initializing app
		mProgress = (ProgressBar) findViewById(R.id.progress);
		mText = (TextView) findViewById(R.id.text);
		mText.setVisibility(View.GONE);

		// if (savedInstanceState == null) {
		// getFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment())
		// .commit();
		// }

		mFlickrClient = new FlickrClient();

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

		GridFragment frag = new GridFragment();

		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.container, frag);
		ft.commitAllowingStateLoss();
	}

	/** Get the flickr photos we are browsing.
	 * 
	 * @return
	 */
	public List<FlickrPhoto> getPhotos() {
		return mPhotos;
	}

}
