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


import com.google.gson.Gson;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.Fragments.ChatFragment.ChatTestClass.ChatActivityTest;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.R;
import com.vita.godealsashi.registration.UserSetupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button user_edit_profile, full_button;

    private CircleImageView profileImage;

    private TextView user_fullnameView, user_locationView, user_statusView, user_ageView;

    private Context mContext;



    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
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
        full_button = v.findViewById(R.id.button_full);

        if(currentUser != null){



            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

            String name = preferences.getString("current_name", "");
            String lastname = preferences.getString("current_lastname", "");
            String city = preferences.getString("current_city", "");
            String age = preferences.getString("current_age", "");
            String image = preferences.getString("current_image", "");


            if(!name.equalsIgnoreCase(""))
            {
                name = name + " " + lastname;  /* Edit the value here*/
                user_fullnameView.setText(name);
                user_locationView.setText(city);
                user_ageView.setText(age);

                Glide.with(mContext)
                        .load(image)
                        .into(profileImage);


            }


        } else {



        }



            //getUserData(currentUser);

            full_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ChatActivityTest.class);
                    startActivity(intent);
                }
            });



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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
