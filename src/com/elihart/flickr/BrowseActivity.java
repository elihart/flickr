package com.elihart.flickr;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class BrowseActivity extends Activity {
	/** Progress bar for loading initial image list. */
	private ProgressBar mProgress;
	private FlickrClient mFlickrClient;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        
        // show progress while initializing app
        mProgress = (ProgressBar) findViewById(R.id.progress);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        mFlickrClient = new FlickrClient();
        
        mFlickrClient.getInterestingPhotos(new Callback<FlickrResponse>() {
			
			@Override
			public void success(FlickrResponse arg0, Response arg1) {
				System.out.println(arg0.getPhotos().toString());		
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				System.out.println(arg0.toString());
				
			}
		});
        
        
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
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_browse,
					container, false);
			return rootView;
		}
	}

}
