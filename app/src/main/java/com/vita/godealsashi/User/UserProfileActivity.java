package com.vita.godealsashi.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.R;
import com.vita.godealsashi.registration.UserSetupActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {




    private ImageView request_friend_image;

    private TextView user_fullnameView;
    private CircleImageView user_CircleImage;
    private int mCurrent_state;

    private ParseUser objectParseUser;

    private final int VALUE = mCurrent_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final String ownerUser = getIntent().getStringExtra("objectId");
        final ParseUser current_user = ParseUser.getCurrentUser();



        request_friend_image = findViewById(R.id.request_friend_image);
        user_fullnameView = findViewById(R.id.user_full_name);
        user_CircleImage = findViewById(R.id.user_profile_image);
        Glide.with(UserProfileActivity.this)
                .load(R.mipmap.ic_person)
                .into(user_CircleImage);







        request_friend_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCurrent_state == 0){

                    //TODO: create some method or solution for save status requested or not

                    //sendInvite(ownerUser,current_user);
                    sendInvite(current_user, ownerUser);
                    mCurrent_state = 1;
                    request_friend_image.setImageResource(R.drawable.ic_request_red_24dp);



                } else if(mCurrent_state == 1){

                    deleteSentInvite(ownerUser, current_user);
                    mCurrent_state = 0;
                    request_friend_image.setImageResource(R.drawable.ic_action_deal);
                    //delete request

                }

            }
        });

        getUserData(ownerUser);
        getIsinvited(ownerUser, current_user);

    }

    @Override
    protected void onPause() {
        super.onPause();

     /*   private final int VALUE = mCurrent_state;*/

       /* SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences("a", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(Value, mCurrent_state);
// Replace `putInt` with `putString` if your value is a String and not an Integer.
        editor.commit();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void deleteSentInvite(final String object_user_id, final ParseUser current_user){

        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("user", current_user);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null){

                    if(object.getSent() == null){

                        //THERE NO OBJECT

                    } else if(object.getSent() != null){


                        JSONArray sentArray = object.getSent();


                        for (int i = 0; i < sentArray.length(); i++) {

                            try {
                                while(sentArray.get(i).equals(object_user_id)){

                                    sentArray.remove(i);

                                    String current_user_object = object.getObjectid();
                                    deleteRecivesInvite(object_user_id, current_user_object);
                                    object.setSent(sentArray);
                                    object.saveInBackground();

                                }


                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {
                        Toast.makeText(UserProfileActivity.this, "There is no object", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void deleteRecivesInvite(final String object_user_id, final String current_user){

        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("objectid", object_user_id);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null){

                    if(object.getRecived() == null){

                        //THERE NO OBJECT

                    } else if(object.getRecived() != null){

                        JSONArray recivedArray = object.getRecived();
                        try {
                            for (int i = 0; i < recivedArray.length(); i++) {

                                    while(recivedArray.get(i).equals(current_user)){

                                        recivedArray.remove(i);
                                        object.setRecived(recivedArray);
                                        object.saveInBackground();
                                        Toast.makeText(UserProfileActivity.this, "Invite canceled",Toast.LENGTH_SHORT).show();

                                    }



                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        Toast.makeText(UserProfileActivity.this, "There is no object", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }





    private void reciveInvite(final String object_user_id, final String current_user){

        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("objectid", object_user_id);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null){

                    if(object.getRecived() == null || object.getRecived().length() <= 0){

                        JSONArray recivedArray = new JSONArray();
                        recivedArray.put(current_user);

                        object.setRecived(recivedArray);
                        object.saveInBackground();

                        Toast.makeText(UserProfileActivity.this, "Invited to collegues",Toast.LENGTH_SHORT).show();

                    } else if(object.getRecived() != null){

                        JSONArray recivedArray = object.getRecived();

                        try{
                            for (int i = 0; i < recivedArray.length(); i++) {

                                while(recivedArray.get(i).equals(current_user)){

                                    recivedArray.remove(i);
                                    object.setRecived(recivedArray);
                                    object.saveInBackground();

                                }

                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        recivedArray.put(current_user);
                        object.setRecived(recivedArray);
                        object.saveInBackground();

                    }

                    } else {

                    Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    private void sendInvite(ParseUser current_user, final String object_user_id){
        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("user", current_user);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null){


                    if(object.getSent() == null  || object.getSent().length() <= 0){

                        JSONArray sentArray = new JSONArray();
                        sentArray.put(object_user_id);
                        object.setSent(sentArray);

                        String current_user_object = object.getObjectid();

                        reciveInvite(object_user_id, current_user_object);

                        object.saveInBackground();

                    } else if(object.getSent() != null){

                        JSONArray sentArray = object.getSent();

                        try{
                            for (int i = 0; i < sentArray.length(); i++) {

                                while(sentArray.get(i).equals(object_user_id)){

                                    sentArray.remove(i);
                                    object.setSent(sentArray);
                                    object.saveInBackground();

                                }

                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        sentArray.put(object_user_id);
                        object.setSent(sentArray);
                        String current_user_object = object.getObjectid();
                        reciveInvite(object_user_id, current_user_object);

                        object.saveInBackground();

                        }

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
                    //String city = object.getCity();
                    ParseUser parseUser = object.getOwner();

                    String Objectid = object.getObjectId();

                    user_fullnameView.setText(name + " " + lastname);
                    objectParseUser = parseUser;

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

    private void getIsinvited(final String object_user, final ParseUser current_user){


        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);
        queryExist.whereEqualTo("user", current_user);

        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null){

                    try{

                        for (int i = 0; i < object.getSent().length(); i++) {

                            if(object.getSent().get(i).equals(object_user)){

                                mCurrent_state = 1;

                                request_friend_image.setImageResource(R.drawable.ic_request_red_24dp);

                                Toast.makeText(UserProfileActivity.this, "SAY SOMETHING PLEASE", Toast.LENGTH_SHORT).show();

                            }

                        }


                    }catch (Exception e1){

                        Log.d("GetstatusSended: ", e1.toString());

                    }

                } else {

                    mCurrent_state = 0;

                    Toast.makeText(UserProfileActivity.this, "NONONO", Toast.LENGTH_SHORT).show();



                }

            }
        });


    }

    // TODO private void checkForRequestStatus()





}