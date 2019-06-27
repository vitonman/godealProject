package com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChooseRegionActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;


import com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChoosePositionActivity.PositionDataObject;
import com.vita.godealsashi.Fragments.DealFragment.DealFragment;
import com.vita.godealsashi.R;


class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.MyViewHolder> {

    public Context context;

    List<RegionDataObject> regionDataList;
    public RegionAdapter(List<RegionDataObject> regionDataList) {
        this.regionDataList=regionDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.position_list_row, viewGroup, false);



        context = viewGroup.getContext();



        return new MyViewHolder(itemView);

    }




    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        final RegionDataObject data=regionDataList.get(i);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        viewHolder.parent.setBackgroundColor(currentColor);
        viewHolder.name.setText(data.name);
        viewHolder.age.setText(String.valueOf(data.id));
        final int regionId = data.id;
        final int positionId = data.positionId;







        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppCompatActivity activity = (AppCompatActivity) context;

                Fragment dealFragment = new DealFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("regionId", regionId);
                bundle.putInt("positionId", positionId);
                dealFragment.setArguments(bundle);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, dealFragment).addToBackStack(null).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return regionDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,age;
        LinearLayout parent;
        View mView;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            name=itemView.findViewById(R.id.name);
            age=itemView.findViewById(R.id.age);
            mView = itemView;
        }
    }
}