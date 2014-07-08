package com.elihart.flickr;

import retrofit.Callback;
import retrofit.RestAdapter;

public class FlickrClient {
	private static final String END_POINT = "https://api.flickr.com/services/rest";
	private static FlickrApi mApi;
	private static final String API_KEY = "658d74faac0800a6e75ee2503d7e0239";
	private static final String JSON_FORMAT = "json";
	private static final int NO_JSON_CALLBACK = 1;
	private static final String EXTRAS = "url_o, url_c, url_n, owner_name, description, views";

	public FlickrClient() {
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(END_POINT)
				.build();

		mApi = adapter.create(FlickrApi.class);
	}

	public void getInterestingPhotos(Callback<FlickrResponse> cb) {		
		mApi.images("flickr.interestingness.getList", API_KEY, JSON_FORMAT,
				NO_JSON_CALLBACK, EXTRAS, cb);
	}
	
	public void search(String query, Callback<FlickrResponse> cb){		
		mApi.search("flickr.photos.search", query, API_KEY, JSON_FORMAT,
				NO_JSON_CALLBACK, EXTRAS, cb);
	}

}
