package com.vita.godealsashi.Fragments.ProfileFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.R;
import com.vita.godealsashi.registration.UserSetupActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button user_edit_profile;

    private CircleImageView profileImage;

    private TextView user_fullnameView, user_locationView, user_statusView, user_ageView;

    private Context mContext;



    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ParseUser currentUser = ParseUser.getCurrentUser();

        profileImage = v.findViewById(R.id.user_image_request);

        Glide.with(mContext)
                .load(R.mipmap.ic_person)
                .into(profileImage);



        user_fullnameView = v.findViewById(R.id.user_nameView);
        user_locationView = v.findViewById(R.id.user_locationView);
        user_statusView = v.findViewById(R.id.user_statusView);
        user_ageView = v.findViewById(R.id.age_textView);
        user_edit_profile = v.findViewById(R.id.edit_profile);

        if(currentUser != null){

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String name = preferences.getString("Name", "");
            if(!name.equalsIgnoreCase(""))
            {
                name = name + "  Sethi";  /* Edit the value here*/
                user_fullnameView.setText(name);
            }

            //getUserData(currentUser);

        } else {

            //move to login page
        }



        user_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserSetupActivity.class);
                startActivity(intent);
            }
        });



        return v;


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void getUserData(ParseUser currentUser){

        final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);
        queryExist.whereEqualTo("owner", currentUser);
        queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
            @SuppressLint("CheckResult")
            @Override
            public void done(CustomUser object, ParseException e) {

                if(e == null){
                    String name = object.getName();
                    int age = object.getAge();
                    String lastname = object.getLastname();
                    String city = object.getCity();



                    user_ageView.setText("Age: " + Integer.toString(age));
                    user_fullnameView.setText(name + " " + lastname);
                    user_locationView.setText("From: " + city);


                    RequestOptions placeholderRequest = new RequestOptions();
                    placeholderRequest.placeholder(R.mipmap.ic_person);

                    Glide.with(mContext)
                            .setDefaultRequestOptions(placeholderRequest)
                            .load(object.getImage().getUrl())
                            .into(profileImage);

                    //Toast.makeText(getActivity(), "Data exist", Toast.LENGTH_SHORT).show();
                    Log.i("Message: ", "Data exist");

                } else {

                    //Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                    Log.i("Message: ", "No data exist.");

                }


            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
