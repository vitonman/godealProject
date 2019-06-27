package com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChooseRegionActivity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


        Bundle bundle = this.getArguments();
        if (bundle != null) {

            int anPosInt = bundle.getInt("positionId", 0);

            StudentDataPrepare(anPosInt);

            Toast.makeText(getContext(), Integer.toString(anPosInt), Toast.LENGTH_SHORT).show();

        }



        return v;
    }



    private void StudentDataPrepare(int position) {
        RegionDataObject data = new RegionDataObject("Tallinn", 0,position);
        regionDataList.add(data);
        data = new RegionDataObject("Tartu", 1,position);
        regionDataList.add(data);
        data = new RegionDataObject("Pärnu", 2,position);
        regionDataList.add(data);
        data = new RegionDataObject("Rakvere", 3,position);
        regionDataList.add(data);
        data = new RegionDataObject("Saaremaa", 4,position);
        regionDataList.add(data);
        data = new RegionDataObject("Narva", 5,position);
        regionDataList.add(data);
        data = new RegionDataObject("Haapsalu", 6,position);
        regionDataList.add(data);
        data = new RegionDataObject("Kohtla-jarve", 7,position);
        regionDataList.add(data);
        data = new RegionDataObject("Elva", 8,position);
        regionDataList.add(data);
        data = new RegionDataObject("Kuresaare", 9,position);
        regionDataList.add(data);
        data = new RegionDataObject("Viljandi", 10,position);
        regionDataList.add(data);
        data = new RegionDataObject("Rõngu", 11,position);
        regionDataList.add(data);
        data = new RegionDataObject("Valga", 12,position);
        regionDataList.add(data);
        data = new RegionDataObject("Roju", 13,position);
        regionDataList.add(data);

        Collections.sort(regionDataList, new Comparator<RegionDataObject>() {
            @Override
            public int compare(RegionDataObject o1, RegionDataObject o2) {
                return o1.name.compareTo(o2.name);
            }
        });


    }
}
