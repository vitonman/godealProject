package com.vita.godealsashi.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

public class UserProfileActivity extends AppCompatActivity {




    ConstraintLayout constraintLayout;

    AppCompatEditText username, password;
    TextInputLayout userLayout, passwordLayout;

    TextView user_fullnameView;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String ownerUser = getIntent().getStringExtra("objectId");

        user_fullnameView = findViewById(R.id.user_full_name);


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

                /*    Glide.with(mContext)
                            .setDefaultRequestOptions(placeholderRequest)
                            .load(object.getImage().getUrl())
                            .into(profileImage);
*/
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

}