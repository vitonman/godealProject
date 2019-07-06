package com.vita.godealsashi.Fragments.navigationDrawerFragments.OffersFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import com.parse.SaveCallback;
import com.vita.godealsashi.Fragments.ChatFragment.ChatTestClass.ChatActivityTest;
import com.vita.godealsashi.OfferActivities.ReciveOffer;
import com.vita.godealsashi.OfferActivities.WorkOffer;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.DealList;
import com.vita.godealsashi.ParseClasses.FriendList;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.ParseClasses.OfferInvite;
import com.vita.godealsashi.R;
import com.vita.godealsashi.UserProfileClasses.UserProfileActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfferRecycleAdapter extends RecyclerView.Adapter<OfferRecycleAdapter.ViewHolder> {

    public List<CustomUser> userList;

    public Context context;

    private Button add_friend_btn;

    int button_set;

    public OfferRecycleAdapter(List<CustomUser> userList) {
        this.userList = userList;

    }

    @NonNull
    @Override
    public OfferRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_accept_friend_item, viewGroup, false);
        context = viewGroup.getContext();

        button_set = 0;

        add_friend_btn = view.findViewById(R.id.add_friend_btn);


        return new OfferRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OfferRecycleAdapter.ViewHolder viewHolder, int i) {

        viewHolder.setIsRecyclable(false);

        String name = userList.get(i).getName();
        viewHolder.setUserName(name);

        String image = userList.get(i).getImage().getUrl();

        viewHolder.setUserImage(image);

        final String owner = userList.get(i).getOwner();

        final String objectId = userList.get(i).getObjectId();

        viewHolder.user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent commentIntent = new Intent(context, UserProfileActivity.class);

                commentIntent.putExtra("ParseObjectOwner", owner);
                commentIntent.putExtra("objectId", objectId); // NEEED
                commentIntent.putExtra("IsFriend",false);
                context.startActivity(commentIntent);

            }
        });

        final String targetId = userList.get(i).getObjectId();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String ownerId = preferences.getString("current_ownerId", "");

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toReciveOffer = new Intent(context, ReciveOffer.class);

                toReciveOffer.putExtra("targetUserId", ownerId);
                toReciveOffer.putExtra("ownerUserId", targetId);

                context.startActivity(toReciveOffer);


            }
        });

        //add to friends




        //----------------------------------------------------


        add_friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DealList checkDealList = getDealObject(ownerId, targetId);


                if(checkDealList != null){

                    Toast.makeText(context, "In list", Toast.LENGTH_SHORT).show();

                } else {



                }





                OfferRecycleAdapter.this.notifyDataSetChanged();


            }
        });


    }

    public DealList getDealObject(String ownerId, String targetId){

        ParseQuery<DealList> query = ParseQuery.getQuery(DealList.class);
        query.whereEqualTo("owner", ownerId);
        query.whereEqualTo("targetId", targetId);
        try {
            return query.getFirst();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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

            user_name_text = mView.findViewById(R.id.user_message);
            user_name_text.setText(name);


        }

        public void setUserImage(String img_url){

            user_image = mView.findViewById(R.id.user_image_request);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.mipmap.ic_person);

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(img_url).into(user_image);
        }

    }





}
