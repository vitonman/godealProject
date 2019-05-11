package com.vita.godealsashi.Fragments.DealFragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.vita.godealsashi.CustomClasses.CustomUser;

import com.vita.godealsashi.R;
import com.vita.godealsashi.User.UserProfileActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DealRecycleAdapter extends RecyclerView.Adapter<DealRecycleAdapter.ViewHolder> {

    public List<CustomUser> userList;

    public Context context;



    public DealRecycleAdapter(List<CustomUser> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //there i put it to xml file
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_profile_item, viewGroup, false);
        context = viewGroup.getContext();



        return new DealRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setIsRecyclable(false);

        Number age = userList.get(i).getAge();
        viewHolder.setAge(age);


        String name = userList.get(i).getName();
        viewHolder.setUserName(name);

        String image = userList.get(i).getImage().getUrl();
        viewHolder.setUserImage(image);

        final String objectId = userList.get(i).getObjectId();

        viewHolder.user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(context, UserProfileActivity.class);
                commentIntent.putExtra("objectId", objectId); // NEEED
                context.startActivity(commentIntent);
            }
        });




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


        public void setAge(Number age){

            user_age_text = mView.findViewById(R.id.user_city_text);
            user_age_text.setText(age.toString());


        }

        public void setUserName(String name){

            user_name_text = mView.findViewById(R.id.user_name_edit);
            user_name_text.setText(name);


        }

        public void setUserImage(String img_url){

            user_image = mView.findViewById(R.id.user_imageView);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.mipmap.ic_person);

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(img_url).into(user_image);
        }

    }




}
