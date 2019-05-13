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
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.R;

import org.json.JSONArray;
import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {




    private ImageView request_friend_image;

    private TextView user_fullnameView;
    private CircleImageView user_CircleImage;
    private int mCurrent_state;

    private ParseUser objectParseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final String ownerUser = getIntent().getStringExtra("objectId");

        //final String clickedUserParse = getIntent().getStringExtra("owner");


        ParseUser current_user = ParseUser.getCurrentUser();


        // now returned in current_user_customuser_objId objectId value of current user.
        Toast.makeText(UserProfileActivity.this, current_user.toString(), Toast.LENGTH_SHORT).show();

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

                    mCurrent_state = 1;



                } else if(mCurrent_state == 1){

                    //delete request


                }

            }
        });

        getUserData(ownerUser);

    }


    private void sendInvite(ParseUser current_user, final ParseUser objectParseUser){
        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("owner", current_user);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null){



                } else {



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





}