package com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChooseRegionActivity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vita.godealsashi.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class RegionFragment extends Fragment {

    private RecyclerView recyclerView;
    private RegionAdapter regionAdapter;
    private List<RegionDataObject> regionDataList = new ArrayList<>();
    Context context;

    public RegionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_region, container, false);

        recyclerView = v.findViewById(R.id.recycle_view_region);
        regionAdapter = new RegionAdapter(regionDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(regionAdapter);


        StudentDataPrepare();

        return v;
    }

    private void StudentDataPrepare() {
        RegionDataObject data = new RegionDataObject("Tallinn", 1);
        regionDataList.add(data);
        data = new RegionDataObject("Tartu", 2);
        regionDataList.add(data);
        data = new RegionDataObject("Pärnu", 3);
        regionDataList.add(data);
        data = new RegionDataObject("Rakvere", 4);
        regionDataList.add(data);
        data = new RegionDataObject("Saaremaa", 5);
        regionDataList.add(data);
        data = new RegionDataObject("Narva", 6);
        regionDataList.add(data);
        data = new RegionDataObject("Haapsalu", 7);
        regionDataList.add(data);
        data = new RegionDataObject("Kohtla-jarve", 8);
        regionDataList.add(data);
        data = new RegionDataObject("Elva", 9);
        regionDataList.add(data);
        data = new RegionDataObject("Kuresaare", 10);
        regionDataList.add(data);
        data = new RegionDataObject("Viljandi", 11);
        regionDataList.add(data);
        data = new RegionDataObject("Rõngu", 12);
        regionDataList.add(data);
        data = new RegionDataObject("Valga", 13);
        regionDataList.add(data);
        data = new RegionDataObject("Roju", 14);
        regionDataList.add(data);

        Collections.sort(regionDataList, new Comparator<RegionDataObject>() {
            @Override
            public int compare(RegionDataObject o1, RegionDataObject o2) {
                return o1.name.compareTo(o2.name);
            }
        });


    }
}
