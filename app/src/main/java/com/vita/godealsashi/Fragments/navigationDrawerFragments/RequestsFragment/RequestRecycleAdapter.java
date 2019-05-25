package com.vita.godealsashi.Fragments.navigationDrawerFragments.RequestsFragment;

import android.content.Context;
import android.content.Intent;
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
import com.parse.ParseUser;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.FriendList;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.R;
import com.vita.godealsashi.User.UserProfileActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestRecycleAdapter extends RecyclerView.Adapter<RequestRecycleAdapter.ViewHolder> {

    public List<CustomUser> userList;

    public Context context;

    private Button user_button;

    int button_set;

    public RequestRecycleAdapter(List<CustomUser> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RequestRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_accept_friend_item, viewGroup, false);
        context = viewGroup.getContext();

        button_set = 0;

        user_button = view.findViewById(R.id.add_friend_btn);


        return new RequestRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestRecycleAdapter.ViewHolder viewHolder, int i) {

        viewHolder.setIsRecyclable(false);

        String name = userList.get(i).getName();
        viewHolder.setUserName(name);

        String image = userList.get(i).getImage().getUrl();

        viewHolder.setUserImage(image);

        final ParseUser owner = userList.get(i).getOwner();

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

        //add to friends

        //---------------------------------------------------
        final ParseUser whoSendYo = userList.get(i).getOwner();

        final ParseUser current_user = ParseUser.getCurrentUser();

        //----------------------------------------------------


        user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ParseQuery<FriendList> queryFriend = ParseQuery.getQuery(FriendList.class);
                queryFriend.whereEqualTo("owner", current_user);
                queryFriend.whereEqualTo("target", whoSendYo);

                queryFriend.getFirstInBackground(new GetCallback<FriendList>() {
                    @Override
                    public void done(FriendList object, ParseException e) {

                        if(e == null){

                            Toast.makeText(context, "Already", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(context, "Added to friendlist", Toast.LENGTH_SHORT).show();
                            FriendList friend = new FriendList();
                            friend.setOwner(current_user);
                            friend.setTarget(whoSendYo);
                            friend.saveInBackground();

                            FriendList target_friend = new FriendList();
                            target_friend.setOwner(whoSendYo);
                            target_friend.setTarget(current_user);
                            target_friend.saveInBackground();

                            final ParseQuery<Invite> queryInvite = ParseQuery.getQuery(Invite.class);
                            queryInvite.whereEqualTo("owner", whoSendYo);
                            queryInvite.whereEqualTo("target", current_user);
                            queryInvite.getFirstInBackground(new GetCallback<Invite>() {
                                @Override
                                public void done(Invite object, ParseException e) {

                                    object.deleteInBackground();

                                }
                            });

                        }

                    }
                });





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
