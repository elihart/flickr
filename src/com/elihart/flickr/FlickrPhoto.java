package com.elihart.flickr;

public class FlickrPhoto {
	private String id;
	private String owner;
	private String title;
	private String secret;
	private String server;
	private String farm;
	
	private static final String IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";
	
	public enum FlickrSize {
		SMALL("n"), ORIGINAL("o");
		
		/** The letter suffix to attach to the url to get this size. */
		private String suffix;
		
		private FlickrSize(String suffix){
			this.suffix = suffix;
		}
		
		public String getSuffix(){
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
		return String.format(IMAGE_URL, farm, server, id, secret, size.getSuffix());
	}

}
