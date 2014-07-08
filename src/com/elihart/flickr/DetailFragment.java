package com.elihart.flickr;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elihart.flickr.FlickrPhoto.FlickrSize;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class DetailFragment extends Fragment {
	private BrowseActivity mActivity;
	/** The view to show the photo in. */
	private ImageView mImageView;
	/** The photo to show. */
	private FlickrPhoto mPhoto;
	/** Progress to show while image loads. */
	private ProgressBar mProgress;
	private TextView mTitle;
	private TextView mOwner;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (BrowseActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// retain this fragment
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_detail, container, false);

		mImageView = (ImageView) view.findViewById(R.id.image);
		mProgress = (ProgressBar) view.findViewById(R.id.progress);
		mTitle = (TextView) view.findViewById(R.id.title);
		mOwner = (TextView) view.findViewById(R.id.owner);
		if(mPhoto != null){
			showPhoto(mPhoto);
		}
		return view;
	}

	/**
	 * Show the given photo.
	 * 
	 * @param photo
	 */
	public void showPhoto(FlickrPhoto photo) {
		mPhoto = photo;
		// set photo details
		mTitle.setText(mPhoto.getTitle());
		mOwner.setText(mPhoto.getOwnerName());
		
		// load image from url
		String url = mPhoto.getUrl(FlickrSize.ORIGINAL);
		ImageLoader.getInstance().displayImage(url, mImageView, null,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						mProgress.setProgress(0);
						mImageView.setImageDrawable(null);
						mProgress.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						mImageView.setImageDrawable(null);
						mProgress.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						mProgress.setVisibility(View.GONE);
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {
						mProgress.setProgress(Math.round(100.0f * current
								/ total));
					}
				});
	}

}
