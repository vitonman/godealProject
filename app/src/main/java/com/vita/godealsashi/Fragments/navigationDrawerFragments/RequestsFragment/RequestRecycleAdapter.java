package com.vita.godealsashi.Fragments.navigationDrawerFragments.RequestsFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.Fragments.navigationDrawerFragments.ColleguesFragment.ColleguesRecycleAdapter;
import com.vita.godealsashi.R;
import com.vita.godealsashi.User.FriendRequest;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestRecycleAdapter extends RecyclerView.Adapter<RequestRecycleAdapter.ViewHolder> {

    public List<CustomUser> userList;

    public List<FriendRequest> requestsList;

    public Context context;


    public RequestRecycleAdapter(List<CustomUser> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RequestRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_list_item, viewGroup, false);
        context = viewGroup.getContext();



        return new RequestRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestRecycleAdapter.ViewHolder viewHolder, int i) {

        viewHolder.setIsRecyclable(false);


        String name = userList.get(i).getName();
        viewHolder.setUserName(name);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;

        //user class data
        private TextView user_age_text;
        private TextView user_name_text;
        //private CircleImageView user_image;
        private CircleImageView user_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setUserName(String name){

            user_name_text = mView.findViewById(R.id.user_name_edit);
            user_name_text.setText(name);


        }

    }
}
