package commontestware.com.delieveryordermgmt.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.adapter.GridAdapter;
import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.database.DbHelper;
import commontestware.com.delieveryordermgmt.model.Modelone;

public class CategoryFragment extends Fragment {
    GridView categorylist;
    GridAdapter categoryListAdapter;
    int index;
    ArrayList<Modelone> category_list;
    public Integer[] mThumbIds = {
            R.drawable.crabs};
    DbHelper helper;

    TextView nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        helper = new DbHelper(getActivity());
        View rootView = inflater.inflate(R.layout.fragmentcategory, container, false);
        categorylist = (GridView) rootView.findViewById(R.id.grid_view);
        index = categorylist.getFirstVisiblePosition();
        categorylist.smoothScrollToPosition(index);
        nodata = (TextView) rootView.findViewById(R.id.nodata);

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getCategoryDetails();
    }

    @Override
    public void onResume() {
        super.onResume();
        getCategoryDetails();

    }

    public void getCategoryDetails() {


        //category_list = helper.getCategoryDetails(Pref_Storage.getDetail(getActivity(), "email"));
        category_list = helper.getCategoryDetails();
        if (category_list.size() > 0 && !category_list.isEmpty()) {
            nodata.setVisibility(View.GONE);
            categorylist.setVisibility(View.VISIBLE);
            categoryListAdapter = new GridAdapter(getActivity(), category_list);
            categorylist.setAdapter(categoryListAdapter);
        } else {
            categorylist.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
    }

}

