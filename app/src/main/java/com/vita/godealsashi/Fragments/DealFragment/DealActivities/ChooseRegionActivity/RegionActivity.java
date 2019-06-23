package com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChooseRegionActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vita.godealsashi.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RegionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RegionAdapter regionAdapter;
    private List<RegionDataObject> regionDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooce_region);

        recyclerView = findViewById(R.id.recycle_view_region);
        regionAdapter = new RegionAdapter(regionDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(regionAdapter);
        StudentDataPrepare();
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