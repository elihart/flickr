package com.elihart.flickr;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FlickrApi {
	@GET("/")
	void images(@Query("method") String method,
			@Query("api_key") String apiKey, @Query("format") String format,
			@Query("nojsoncallback") int jsonCallback, @Query("extras") String extras, Callback<FlickrResponse> cb);

}
