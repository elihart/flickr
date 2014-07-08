package com.elihart.flickr;

public class FlickrPhoto {
	private String id;
	private String owner;
	private String ownername;
	//private String description; // Is a json object with _content string
	private String title;
	private String secret;
	private String server;
	private String farm;
	/** Original size. */
	private String url_o;
	/** Medium size. */
	private String url_c;
	/** Small size. */
	private String url_n;

	private static final String IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";

	public enum FlickrSize {
		SMALL("n"), ORIGINAL("o");

		/** The letter suffix to attach to the url to get this size. */
		private String suffix;

		private FlickrSize(String suffix) {
			this.suffix = suffix;
		}

		public String getSuffix() {
			return suffix;
		}
	}

	public String getId() {
		return id;
	}

	/**
	 * Get the url where the image is.
	 * 
	 * @return
	 */
	public String getUrl(FlickrSize size) {
		// return String.format(IMAGE_URL, farm, server, id, secret,
		// size.getSuffix());
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

//	public String getDescription() {
//		return description;
//	}

}
