package com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChoosePositionActivity;

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


public class PositionFragment extends Fragment {

    private RecyclerView recyclerView;
    private PositionAdapter positionAdapter;
    private List<PositionDataObject> positionDataList = new ArrayList<>();
    Context context;

    public PositionFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_position, container, false);


        recyclerView = v.findViewById(R.id.recycle_view_position);
        positionAdapter = new PositionAdapter(positionDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(positionAdapter);
        StudentDataPrepare();
        return v;
    }

    private void StudentDataPrepare() {
        PositionDataObject data = new PositionDataObject("Construction/Ehitus", 1);
        positionDataList.add(data);
        data = new PositionDataObject("Transportation", 2);
        positionDataList.add(data);
        data = new PositionDataObject("Education/Koolitus", 3);
        positionDataList.add(data);
        data = new PositionDataObject("Consultation", 4);
        positionDataList.add(data);
        data = new PositionDataObject("Media", 5);
        positionDataList.add(data);
        data = new PositionDataObject("Mechanics", 6);
        positionDataList.add(data);
        data = new PositionDataObject("Law", 7);
        positionDataList.add(data);
        data = new PositionDataObject("Finance", 8);
        positionDataList.add(data);
        data = new PositionDataObject("Banking", 9);
        positionDataList.add(data);
        data = new PositionDataObject("Photo/Video", 10);
        positionDataList.add(data);
        data = new PositionDataObject("Organizacija Prazdnikov", 11);
        positionDataList.add(data);
        data = new PositionDataObject("Banking", 12);
        positionDataList.add(data);
        data = new PositionDataObject("Car", 13);
        positionDataList.add(data);
        data = new PositionDataObject("IT and informatics", 14);
        positionDataList.add(data);


        Collections.sort(positionDataList, new Comparator<PositionDataObject>() {
            @Override
            public int compare(PositionDataObject o1, PositionDataObject o2) {
                return o1.name.compareTo(o2.name);
            }
        });
    }



}
