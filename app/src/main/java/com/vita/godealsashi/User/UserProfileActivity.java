package com.vita.godealsashi.User;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.R;

import org.json.JSONArray;
import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {




    ImageView request_friend_image;

    TextView user_fullnameView;
    CircleImageView user_CircleImage;
    private int mCurrent_state;

    private ParseUser ownerReciver;
    private String current_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final String ownerUser = getIntent().getStringExtra("objectId");

        //final String clickedUserParse = getIntent().getStringExtra("owner");


        ParseUser current_user = ParseUser.getCurrentUser();
        getParseUserById(ownerUser);
        getObjectUserByParse(current_user);


        //String current_user = ParseUser.getCurrentUser().toString();

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

                    ParseUser current_user = ParseUser.getCurrentUser();
                    sendRequest(ownerUser, current_user);
                    getRequest(current_user, current_user_id, ownerReciver);
                    mCurrent_state = 1;

                } else if(mCurrent_state == 1){

                    //delete request

                    deleteRequests(current_user_id, ownerUser);

                }

            }
        });

        getUserData(ownerUser);

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
                    Toast.makeText(UserProfileActivity.this, name + " " + lastname, Toast.LENGTH_SHORT).show();

                } else {

                    //Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                    Log.d("Message: ", "No data exist.");
                    Log.d("Owner: ", ownerUser);

                }


            }
        });

    }

    private void deleteRequests(final String current_user, final String object_user){

        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("sent", object_user);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                Toast.makeText(UserProfileActivity.this, "HELLO", Toast.LENGTH_SHORT).show();




            }
        });



    }

    private void sendRequest(final String obj_user_id, final ParseUser current_user){


        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);


        queryExist.whereEqualTo("owner", current_user);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @SuppressLint("CheckResult")
            @Override
            public void done(FriendRequest object_sender, ParseException e) {

                if(e == null){

                    if(object_sender.getSent() == null){

                        JSONArray requestObjects = new JSONArray();

                        requestObjects.put(obj_user_id);

                        object_sender.setSent(requestObjects);

                        object_sender.saveInBackground();



                    } else {

                        JSONArray requestObjects = object_sender.getSent();

                        for (int i = 0; i < requestObjects.length(); i++) {

                            try {
                                if(requestObjects.get(i) == obj_user_id){

                                    requestObjects.put(obj_user_id);

                                    object_sender.setSent(requestObjects);

                                    object_sender.saveInBackground();

                                }else {

                                    Toast.makeText(UserProfileActivity.this, obj_user_id
                                                    + ", is already exist in your friend request"
                                    , Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }



                    }

                    /*Toast.makeText(UserProfileActivity.this,
                            "Request was sent", Toast.LENGTH_SHORT).show();*/

                } else {

                    Toast.makeText(UserProfileActivity.this,
                            "Something wrong with add a friend.", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

        private void getRequest(final ParseUser current_user, final String current_user_id, final ParseUser objectUser){


        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

                queryExist.whereEqualTo("owner", objectUser);
                queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
                    @Override
                    public void done(FriendRequest object_reciver, ParseException e) {

                        if(e == null){

                            Toast.makeText(UserProfileActivity.this, "Saved and sended",
                                    Toast.LENGTH_SHORT).show();

                            if(object_reciver.getRecived() == null) {

                                JSONArray reciveObjects = new JSONArray();
                                reciveObjects.put(current_user_id);

                                object_reciver.setRecived(reciveObjects);
                                object_reciver.saveInBackground();

                            }else{


                                JSONArray requestObjects = object_reciver.getRecived();

                                for (int i = 0; i < requestObjects.length(); i++) {

                                    try {
                                        if(requestObjects.get(i) == current_user_id){

                                            requestObjects.put(current_user_id);

                                            object_reciver.setRecived(requestObjects);

                                            object_reciver.saveInBackground();

                                        }else {

                                            Toast.makeText(UserProfileActivity.this, current_user_id +
                                                            ", is already exist in your friend request"
                                                    , Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }

                                }

                            }

                        } else {

                            Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            //SOMETHING WRONG

                        }

                    }
                });

    }


    private ParseUser getParseUserById(final String objectId){

        final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);

        queryExist.whereEqualTo("objectId", objectId);
        queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
            @Override
            public void done(CustomUser object, ParseException e) {

                ownerReciver = object.getOwner();

            }
        });

        return ownerReciver;
    }

    private String getObjectUserByParse(final ParseUser parseUser){

        final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);

        queryExist.whereEqualTo("owner", parseUser);
        queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
            @Override
            public void done(CustomUser object, ParseException e) {

                current_user_id = object.getObjectId();

            }
        });

        return current_user_id;
    }

}