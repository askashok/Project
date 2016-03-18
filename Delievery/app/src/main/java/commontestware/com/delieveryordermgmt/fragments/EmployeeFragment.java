package commontestware.com.delieveryordermgmt.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.adapter.ListAdapter;
import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.database.DbHelper;
import commontestware.com.delieveryordermgmt.model.ModelEmployee;

public class EmployeeFragment extends Fragment {

    GridView mylist;
    ListAdapter categorylistdapter;
    ArrayList<ModelEmployee> category_list;
    DbHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentemployee, container, false);
        mylist = (GridView) rootView.findViewById(R.id.emp_list);
        helper = new DbHelper(getActivity());

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

    private void getCategoryDetails() {
        category_list = (ArrayList<ModelEmployee>) helper.employeedetails();
        categorylistdapter = new ListAdapter(getActivity(), category_list);
        mylist.setAdapter(categorylistdapter);

    }


}

