package com.vita.godealsashi.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity{




    private ImageView request_friend_image;

    private TextView user_fullnameView;
    private CircleImageView user_CircleImage;
    private int mCurrent_state;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();

        final ParseUser target_user = intent.getParcelableExtra("ParseObjectOwner");
        final String ownerUser = intent.getStringExtra("objectId");
        final Boolean isFriend = intent.getBooleanExtra("IsFriend", false);
        final ParseUser current_user = ParseUser.getCurrentUser();



        request_friend_image = findViewById(R.id.request_friend_image);
        user_fullnameView = findViewById(R.id.user_full_name);
        user_CircleImage = findViewById(R.id.user_profile_image);
        Glide.with(UserProfileActivity.this)
                .load(R.mipmap.ic_person)
                .into(user_CircleImage);

        if(isFriend){

            request_friend_image.setVisibility(View.INVISIBLE);

        } else {

            mCurrent_state = 0;

            request_friend_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){

                        return;

                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    if(mCurrent_state == 0){

                        createInvite(current_user, target_user);

                        request_friend_image.setImageResource(R.drawable.ic_request_red_24dp);
                        mCurrent_state = 1;


                    }
                    else if (mCurrent_state == 1){

                        deleteInvite(current_user, target_user);

                        mCurrent_state = 0;
                        request_friend_image.setImageResource(R.drawable.ic_action_deal);

                        //delete request

                    }

                }
            });

        }



        getUserData(ownerUser);
        getCheckInvite(current_user, target_user);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void saveProfileOwnerToSent(){

    }

    private void createInvite(final ParseUser current_user, final ParseUser target_user){

        ParseQuery<Invite> queryExist = ParseQuery.getQuery(Invite.class);

        queryExist.whereEqualTo("owner", current_user);
        queryExist.whereEqualTo("target", target_user);

        queryExist.getFirstInBackground(new GetCallback<Invite>() {
           @Override
           public void done(Invite object, ParseException e) {

               if(e == null){


                   Toast.makeText(UserProfileActivity.this, "Already", Toast.LENGTH_SHORT).show();

               } else {


                   Invite newInvite = new Invite();
                   newInvite.setOwner(current_user);
                   newInvite.setTarget(target_user);
                   newInvite.setAccept(false);

                   newInvite.saveInBackground();

                   Toast.makeText(UserProfileActivity.this, "Added", Toast.LENGTH_SHORT).show();

               }

           }
       });



    }

    private void deleteInvite(ParseUser current_user, ParseUser target_user){

        ParseQuery<Invite> queryExist = ParseQuery.getQuery(Invite.class);

        queryExist.whereEqualTo("owner", current_user);
        queryExist.whereEqualTo("target", target_user);

        queryExist.getFirstInBackground(new GetCallback<Invite>() {
            @Override
            public void done(Invite object, ParseException e) {

                object.deleteInBackground();

            }
        });
    }

    private void getCheckInvite(ParseUser current_user, ParseUser target_user){

        ParseQuery<Invite> queryExist = ParseQuery.getQuery(Invite.class);
        queryExist.whereEqualTo("owner", current_user);
        queryExist.whereEqualTo("target", target_user);

        queryExist.getFirstInBackground(new GetCallback<Invite>() {
            @Override
            public void done(Invite object, ParseException e) {

                if(e == null){

                    mCurrent_state = 1;
                    request_friend_image.setImageResource(R.drawable.ic_request_red_24dp);

                } else {

                    Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }



    private void getUserData(final String ownerUser){
        final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);
        queryExist.whereEqualTo("objectId", ownerUser);
        queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
            @SuppressLint("CheckResult")
            @Override
            public void done(CustomUser object, ParseException e) {

                if(e == null){

                    String name = object.getName();
                    int age = object.getAge();
                    String lastname = object.getLastname();
                    String Objectid = object.getObjectId();

                    user_fullnameView.setText(name + " " + lastname);

                    RequestOptions placeholderRequest = new RequestOptions();
                    placeholderRequest.placeholder(R.mipmap.ic_person);

                    Glide.with(UserProfileActivity.this)
                            .setDefaultRequestOptions(placeholderRequest)
                            .load(object.getImage().getUrl())
                            .into(user_CircleImage);

                    //Toast.makeText(getActivity(), "Data exist", Toast.LENGTH_SHORT).show();
                    //Log.d("Message: ", "Data exist");
                    Log.d("Owner: ", name + " " + lastname);
                   // Toast.makeText(UserProfileActivity.this, name + " " + lastname, Toast.LENGTH_SHORT).show();

                } else {

                    //Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                    Log.d("Message: ", "No data exist.");
                    Log.d("Owner: ", ownerUser);

                }


            }
        });

    }


}