package com.elihart.flickr;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GridFragment extends Fragment implements OnItemClickListener {
	private GridView mGrid;
	private List<FlickrPhoto> mPhotos;
	private GridAdapter mAdapter;
	private BrowseActivity mActivity;

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
		View view = inflater.inflate(R.layout.fragment_grid, container, false);

		mGrid = (GridView) view.findViewById(R.id.grid);
		/*
		 * Since we're retaining the fragment we don't always have to create the
		 * adapter.
		 */
		if (mAdapter == null) {
			mAdapter = new GridAdapter(mActivity);
		}
		mGrid.setAdapter(mAdapter);
		mGrid.setOnItemClickListener(this);
		return view;
	}

	/**
	 * Show the given photo list in the grid.
	 * 
	 * @param photos
	 */
	public void showPhotos(List<FlickrPhoto> photos) {
		mPhotos = photos;
		mAdapter.setPhotos(photos);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		// alert the activity that a photo was clicked
		FlickrPhoto photo = mPhotos.get(position);
		mActivity.photoClicked(photo);
	}

}
