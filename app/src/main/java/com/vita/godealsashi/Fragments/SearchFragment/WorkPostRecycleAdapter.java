package com.vita.godealsashi.Fragments.SearchFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.vita.godealsashi.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkPostRecycleAdapter extends RecyclerView.Adapter<WorkPostRecycleAdapter.ViewHolder> {

    public List<WorkPost> postList;

    public Context context;

    private CircleImageView postImage;





    public WorkPostRecycleAdapter(List<WorkPost> postList) {
        this.postList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //there i put it to xml file
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.postlist_list_item, viewGroup, false);
        context = viewGroup.getContext();

        postImage = view.findViewById(R.id.user_post_image);

        Glide.with(context)
                .load(R.mipmap.ic_person)
                .into(postImage);


        return new WorkPostRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setIsRecyclable(false);

  /*      Number age = userList.get(i).getAge();
        viewHolder.setAge(age);
*/

        String name = postList.get(i).getPost();
        viewHolder.setPostName(name);

        String city = postList.get(i).getCity();
        viewHolder.setCityName(city);





        //----------------------------------------------------------------
   /*     String image = userList.get(i).getImage().getUrl();
        viewHolder.setUserImage(image);*/



     /*   final String objectId = postList.get(i).getObjectId();

        viewHolder.user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(context, UserProfileActivity.class);
                commentIntent.putExtra("objectId", objectId); // NEEED
                context.startActivity(commentIntent);
            }
        });*/
        //----------------------------------------------------------------



    }


    @Override
    public int getItemCount() {
        return postList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{


        private View mView;

        //user class data
        private TextView post_name_text;
        private TextView post_city_text;

        //private CircleImageView user_image;
        //private CircleImageView user_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setPostName(String name){

            post_name_text = mView.findViewById(R.id.post_name_text);
            post_name_text.setText(name);


        }

        public void setCityName(String city){

            post_city_text = mView.findViewById(R.id.post_city_text);
            post_city_text.setText(city);


        }


/*        public void setUserImage(String img_url){

            user_image = mView.findViewById(R.id.user_imageView);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.mipmap.ic_person);

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(img_url).into(user_image);
        }

    }*/



    }
}