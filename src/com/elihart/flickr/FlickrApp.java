package com.elihart.flickr;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FlickrApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Create global configuration and initialize ImageLoader with this
		// configuration
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true).cacheInMemory(true)
				.cacheOnDisk(true).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions).build();

		ImageLoader.getInstance().init(config);
	}
}
