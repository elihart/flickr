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
	public void onAttach(Activity activity){
		super.onAttach(activity);
		mActivity = (BrowseActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_grid, container, false);
		
		mGrid = (GridView) view.findViewById(R.id.grid);
		return view;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		/* Get the images from the activity and show them in the grid. */
		mPhotos = mActivity.getPhotos();
		mAdapter = new GridAdapter(mActivity, mPhotos);
		mGrid.setAdapter(mAdapter);
	}

}
