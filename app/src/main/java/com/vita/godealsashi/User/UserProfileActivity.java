package com.vita.godealsashi.User;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
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
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.R;
import com.vita.godealsashi.registration.UserSetupActivity;

import org.json.JSONArray;
import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {




    private ImageView request_friend_image;

    private TextView user_fullnameView;
    private CircleImageView user_CircleImage;
    private int mCurrent_state;

    private ParseUser objectParseUser;

    private String current_user_object_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final String ownerUser = getIntent().getStringExtra("objectId");



        ParseUser current_user = ParseUser.getCurrentUser();

        getCurrentUserObjectId(current_user);
        String userId = current_user_object_id;
        Toast.makeText(UserProfileActivity.this, userId, Toast.LENGTH_SHORT).show();


        request_friend_image = findViewById(R.id.request_friend_image);
        user_fullnameView = findViewById(R.id.user_full_name);
        user_CircleImage = findViewById(R.id.user_profile_image);
        Glide.with(UserProfileActivity.this)
                .load(R.mipmap.ic_person)
                .into(user_CircleImage);


        mCurrent_state = 0; //not friends


        request_friend_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(mCurrent_state == 0){

                    mCurrent_state = 1;
                    sendInvite(ownerUser);


                } else if(mCurrent_state == 1){

                    //delete request


                }

            }
        });

        getUserData(ownerUser);

    }


    private void sendInvite(final String object_user_id){
        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("custom", object_user_id);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null){

                    Toast.makeText(UserProfileActivity.this, "Sucess", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(UserProfileActivity.this, "False", Toast.LENGTH_SHORT).show();

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

    public void ifHaveRequestObject(final ParseUser current_user, final String object_user){

        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("owner", current_user);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if (e == null){

                    //IF FRIENDREQUEST OBJECT EXIST, WATCH FOR object_user

                    JSONArray sentArray = object.getSent();

                    for (int i = 0; i < sentArray.length(); i++) {

                        try {
                            if(object_user.equals(sentArray.get(i))){

                                //already exist
                                request_friend_image.setVisibility(View.INVISIBLE);


                            } else {

                                sentArray.put(object_user);
                                object.setSent(sentArray);
                                object.saveInBackground();

                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }



                } else if (object == null){

                    FriendRequest newuserfriendlist = new FriendRequest();
                    //newuserfriendlist.setOwner(getCurrentUserObjectId(current_user));



                } else {

                    Log.d("Error: ", e.getMessage() );

                }

            }
        });

    }

    private void getCurrentUserObjectId(final ParseUser current_user){



        final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);
        queryExist.whereEqualTo("owner", current_user);
        queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
            @SuppressLint("CheckResult")
            @Override
            public void done(CustomUser object, ParseException e) {

                if(e == null){

                    //Toast.makeText(UserProfileActivity.this, object.getObjectId(), Toast.LENGTH_SHORT).show();

                    current_user_object_id = object.getObjectId();

                } else {

                    //Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                    Log.d("Message: ", "No data exist.");
                    Log.d("Owner: ", current_user_object_id);

                }


            }
        });


    }




}