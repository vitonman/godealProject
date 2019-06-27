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

    private String[] cities = {"Tallinn", "Tartu", "Narva", "Pärnu", "Kohtla-Järve", "Viljandi","Rakvere","Maardu","Kuressaare","Sillamäe","Valga"
            ,"Võru","Jõhvi","Haapsalu","Keila","Paide","Elva","Saue","Põlva","Tapa","Jõgeva","Rapla","Kiviõli","Türi","Põltsamaa","Sindi"
            ,"Paldiski","Kärdla","Kunda","Tõrva","Narva-Jõesuu","Kehra","Loksa","Räpina","Otepää","Tamsalu","Kilingi-Nõmme","Karksi-Nuia","Antsla",
            "Võhma","Mustvee","Lihula","Suure-Jaani","Abja-Paluoja","Püssi","Mõisaküla","Kallaste"};

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


        int i = 0;

        for(String city: cities){

            RegionDataObject data = new RegionDataObject(city, i,position);
            regionDataList.add(data);
            i += 1;
        }

/*

        Collections.sort(regionDataList, new Comparator<RegionDataObject>() {
            @Override
            public int compare(RegionDataObject o1, RegionDataObject o2) {
                return o1.name.compareTo(o2.name);
            }
        });
*/


    }
}
