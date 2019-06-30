package com.vita.godealsashi.UserProfileClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.Fragments.ChatFragment.ChatTestClass.ChatActivityTest;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.ParseClasses.OfferInvite;
import com.vita.godealsashi.R;

import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity{

    boolean offerButtonClicked;


    private ImageView request_friend_image;
    private ImageView messaageBtn;
    private TextView textRequest, abilityText;
    private Button offerButton;
    private TextView user_fullnameView;
    private CircleImageView user_CircleImage;
    private int mCurrent_state;
    private long mLastClickTime = 0;

    private Boolean isFriend = false;

    private Context context;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Intent intent = getIntent();
        final String custom_user_current_id = preferences.getString("current_ownerId", "");
        final String ownerUser = intent.getStringExtra("objectId");
        Set<String> friend_list = preferences.getStringSet("friendlist", null);




        if(friend_list != null) {

            for (String friend: friend_list){

                if(friend.equals(ownerUser)){

                    isFriend = true;

                }

            }

        }


        final ParseUser current_user = ParseUser.getCurrentUser();



        request_friend_image = findViewById(R.id.request_friend_image);
        user_fullnameView = findViewById(R.id.user_full_name);
        user_CircleImage = findViewById(R.id.user_profile_image);
        messaageBtn = findViewById(R.id.to_messages_btn);
        textRequest = findViewById(R.id.text_request);
        abilityText = findViewById(R.id.abillities_text);
        offerButton = findViewById(R.id.offer_button);


        Glide.with(UserProfileActivity.this)
                .load(R.mipmap.ic_person)
                .into(user_CircleImage);

        if(isFriend){

            textRequest.setText("Friend");
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

                        createInvite(custom_user_current_id, ownerUser);

                        request_friend_image.setImageResource(R.drawable.ic_request_red_24dp);
                        mCurrent_state = 1;


                    } else if (mCurrent_state == 1){

                        deleteInvite(custom_user_current_id, ownerUser);

                        mCurrent_state = 0;
                        request_friend_image.setImageResource(R.drawable.ic_action_deal);

                        //delete request

                    }

                }
            });

        }

        messaageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMessages = new Intent(UserProfileActivity.this, ChatActivityTest.class);
                toMessages.putExtra("targetUserId", ownerUser);
                startActivity(toMessages);
            }
        });

        offerButtonClicked = false;
        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  if(!offerButtonClicked){

                    offerButtonClicked = true;

                    createOffer(custom_user_current_id, ownerUser);

                    offerButton.setText("sended");

                } else {

                    offerButtonClicked = false;
                    offerButton.setText("Offer to work");
                    deleteOffer(custom_user_current_id,ownerUser);

                }*/

/*              Intent toOfferIntent = new Intent(UserProfileActivity.this, OfferActivity.class);



              startActivity(toOfferIntent);*/



            }
        });


        getUserData(ownerUser);
        getCheckInvite(current_user.getObjectId(), ownerUser);

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

    private void createInvite(final String current_user, final String targetId){


        ParseQuery<Invite> queryExist = ParseQuery.getQuery(Invite.class);

        queryExist.whereEqualTo("owner", current_user);
        queryExist.whereEqualTo("targetId", targetId);

        queryExist.getFirstInBackground(new GetCallback<Invite>() {
           @Override
           public void done(Invite object, ParseException e) {

               if(e == null){

                   Toast.makeText(UserProfileActivity.this, "Already", Toast.LENGTH_SHORT).show();

               } else {


                   Invite newInvite = new Invite();
                   newInvite.setOwner(current_user);
                   newInvite.setTargetId(targetId);
                   newInvite.setAccept(false);

                   newInvite.saveInBackground();

                   Toast.makeText(UserProfileActivity.this, "Added", Toast.LENGTH_SHORT).show();

               }

           }
       });



    }

    private void deleteInvite(String current_user, String targetId){

        ParseQuery<Invite> queryExist = ParseQuery.getQuery(Invite.class);

        queryExist.whereEqualTo("owner", current_user);
        queryExist.whereEqualTo("targetId", targetId);

        queryExist.getFirstInBackground(new GetCallback<Invite>() {
            @Override
            public void done(Invite object, ParseException e) {

                object.deleteInBackground();

            }
        });
    }

    private void getCheckInvite(String current_user, String targetId){

        ParseQuery<Invite> queryExist = ParseQuery.getQuery(Invite.class);
        queryExist.whereEqualTo("owner", current_user);
        queryExist.whereEqualTo("target", targetId);

        queryExist.getFirstInBackground(new GetCallback<Invite>() {
            @Override
            public void done(Invite object, ParseException e) {

                if(e == null){

                        mCurrent_state = 1;
                        request_friend_image.setImageResource(R.drawable.ic_request_red_24dp);

                } else {

                    //Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void createOffer(final String current_user, final String targetId){


        ParseQuery<OfferInvite> queryExist = ParseQuery.getQuery(OfferInvite.class);

        queryExist.whereEqualTo("owner", current_user);
        queryExist.whereEqualTo("targetId", targetId);

        queryExist.getFirstInBackground(new GetCallback<OfferInvite>() {
            @Override
            public void done(OfferInvite object, ParseException e) {

                if(e == null){

                    Toast.makeText(UserProfileActivity.this, "Already", Toast.LENGTH_SHORT).show();

                } else {


                    OfferInvite newInvite = new OfferInvite();
                    newInvite.setOwner(current_user);
                    newInvite.setTargetId(targetId);

                    newInvite.saveInBackground();

                    Toast.makeText(UserProfileActivity.this, "Added", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }

    private void deleteOffer(String current_user, String targetId){

        ParseQuery<OfferInvite> queryExist = ParseQuery.getQuery(OfferInvite.class);

        queryExist.whereEqualTo("owner", current_user);
        queryExist.whereEqualTo("targetId", targetId);

        queryExist.getFirstInBackground(new GetCallback<OfferInvite>() {
            @Override
            public void done(OfferInvite object, ParseException e) {

                object.deleteInBackground();

            }
        });
    }

    private void getCheckOffer(String current_user, String targetId){

        ParseQuery<OfferInvite> queryExist = ParseQuery.getQuery(OfferInvite.class);
        queryExist.whereEqualTo("owner", current_user);
        queryExist.whereEqualTo("target", targetId);

        queryExist.getFirstInBackground(new GetCallback<OfferInvite>() {
            @Override
            public void done(OfferInvite object, ParseException e) {

                if(e == null){

                    mCurrent_state = 1;
                    request_friend_image.setImageResource(R.drawable.ic_request_red_24dp);

                } else {

                    //Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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