package com.elihart.flickr;

public class FlickrPhoto {
	private String id;
	private String owner;
	private String ownername;
	// private String description; // Is a json object with _content string
	private String title;
	private String secret;
	private String server;
	private String farm;
	// The json info contains urls for different sizes of the image.
	/** Original size. */
	private String url_o;
	/** Medium size. */
	private String url_c;
	/** Small size. */
	private String url_n;

	public enum FlickrSize {
		SMALL(), ORIGINAL();
	}

	/**
	 * Get the url where the image is.
	 * 
	 * @return
	 */
	public String getUrl(FlickrSize size) {
		switch (size) {
		case ORIGINAL:
			// original size is not always available. Fallback to medium.
			if (url_o != null) {
				return url_o;
			} else {
				return url_c;
			}
		case SMALL:
			return url_n;
		default:
			return url_n;
		}
	}

	public String getTitle() {
		return title;
	}

	public String getOwnerName() {
		return ownername;
	}

}
