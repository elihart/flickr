package com.elihart.flickr;

import java.util.List;

/**
 * A json response object from a flickr query.
 * 
 * @author eli
 * 
 */
public class FlickrResponse {
	private ResponsePhotos photos;

	/**
	 * Get the photo objects contained by this query response.
	 * 
	 * @return
	 */
	public List<FlickrPhoto> getPhotos() {
		return photos.photo;
	}

	public class ResponsePhotos {
		private List<FlickrPhoto> photo;
	}
}
