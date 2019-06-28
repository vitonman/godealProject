package com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChoosePositionActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

    private String[] positions = {"Courier services", "Repair and construction", "Logistics", "Cleaning",
            "Online-helper", "Computer-help", "Holidays & Activities", "Design", "Software & web-development",
            "Photo/Video", "Installation or Repair of home appliances", "Health and beauty", "Repair of digital equipment", "Legal assistance",
            "Tutoring and training", "Vehicle repair"};


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
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(positionAdapter);
        StudentDataPrepare();
        return v;
    }



    private void StudentDataPrepare() {

        int i = 0;
        for(String position: positions){
            PositionDataObject data = new PositionDataObject(position, i);
            positionDataList.add(data);
            i += 1;

        }

    }



}
