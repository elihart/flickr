package com.elihart.flickr;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elihart.flickr.FlickrPhoto.FlickrSize;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class GridAdapter extends BaseAdapter {
	private List<FlickrPhoto> mPhotos;
	private LayoutInflater mInflater;

	public GridAdapter(Context context) {
		mPhotos = new ArrayList<FlickrPhoto>();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Update the set of photos to display.
	 * 
	 * @param photos
	 */
	public void setPhotos(List<FlickrPhoto> photos) {
		mPhotos = photos;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mPhotos.size();
	}

	@Override
	public FlickrPhoto getItem(int position) {
		return mPhotos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View view = convertView;
		if (view == null) {
			view = mInflater.inflate(R.layout.grid_image, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.image);
			holder.progress = (ProgressBar) view.findViewById(R.id.progress);
			holder.text = (TextView) view.findViewById(R.id.text);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		FlickrPhoto photo = mPhotos.get(position);
		holder.text.setText(photo.getTitle());
		String url = photo.getUrl(FlickrSize.SMALL);

		ImageLoader.getInstance().displayImage(url, holder.image, null,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						holder.progress.setProgress(0);
						holder.image.setImageDrawable(null);
						holder.progress.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						holder.image.setImageDrawable(null);
						holder.progress.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						holder.progress.setVisibility(View.GONE);
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {
						holder.progress.setProgress(Math.round(100.0f * current
								/ total));
					}
				});

		return view;
	}

	private class ViewHolder {
		public ImageView image;
		public ProgressBar progress;
		public TextView text;
	}
}
