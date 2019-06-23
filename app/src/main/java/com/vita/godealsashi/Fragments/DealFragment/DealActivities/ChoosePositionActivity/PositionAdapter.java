package com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChoosePositionActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import java.util.Random;

import com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChooseRegionActivity.RegionActivity;
import com.vita.godealsashi.R;


class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.MyViewHolder> {

    public Context context;

    List<PositionDataObject> studentDataList;
    public PositionAdapter(List<PositionDataObject> studentDataList) {
        this.studentDataList=studentDataList;
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
    public void onBindViewHolder(final MyViewHolder viewHolder, int i) {
        final PositionDataObject data=studentDataList.get(i);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        viewHolder.parent.setBackgroundColor(currentColor);
        viewHolder.name.setText(data.name);
        viewHolder.age.setText(String.valueOf(data.id));

        final int id = data.id;

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent to_Regions = new Intent(context, RegionActivity.class);
                to_Regions.putExtra("positionId", id);
                context.startActivity(to_Regions);

            }
        });

    }

    @Override
    public int getItemCount() {
        return studentDataList.size();
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