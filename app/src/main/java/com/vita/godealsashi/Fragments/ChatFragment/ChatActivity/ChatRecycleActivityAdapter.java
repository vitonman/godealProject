package com.vita.godealsashi.Fragments.ChatFragment.ChatActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vita.godealsashi.Fragments.ChatFragment.ChatFragment;
import com.vita.godealsashi.Fragments.ChatFragment.ChatRecycleAdapter;
import com.vita.godealsashi.R;

import java.util.List;

public class ChatRecycleActivityAdapter extends RecyclerView.Adapter<ChatRecycleActivityAdapter.ViewHolder> {

    private Context context;
    private List<ChatClass> chatMessages;

    @NonNull
    @Override
    public ChatRecycleActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //there i put it to xml file
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_list_item, viewGroup, false);
        context = viewGroup.getContext();

        return new ChatRecycleActivityAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecycleActivityAdapter.ViewHolder viewHolder, int i) {

        //Here comes setters and get values from db


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
