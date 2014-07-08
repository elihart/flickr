package com.elihart.flickr;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class GridFragment extends Fragment {
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
		if(mAdapter == null){
			mAdapter = new GridAdapter(mActivity);			
		}
		mGrid.setAdapter(mAdapter);
		return view;
	}

	/**
	 * Show the given photo list in the grid.
	 * 
	 * @param photos
	 */
	public void showPhotos(List<FlickrPhoto> photos) {
		mAdapter.setPhotos(photos);
	}

}
