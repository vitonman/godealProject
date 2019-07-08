package com.vita.godealsashi.OfferActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.OfferInvite;
import com.vita.godealsashi.R;

import java.util.ArrayList;
import java.util.List;

public class WorkOffer extends AppCompatActivity {

    Button cancelBtn;

    TextView statusTextView;

    ProgressBar progressBarStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_offer);

        cancelBtn = findViewById(R.id.offerCancelBtn);
        statusTextView = findViewById(R.id.offer_text_view);
        progressBarStatus = findViewById(R.id.offer_progressBar);


        Intent intent = new Intent();
        String ownerId = intent.getStringExtra("ownerUserId");
        String targetId = intent.getStringExtra("targetUserId");

        OfferInvite invite = getOffer(ownerId, targetId);


    }

    //TODO: WE CAN ADD LITTLE MESSAGE THERE OR SOMETHING LIKE THAT

    private OfferInvite getOffer(String ownerId, String targetId){
        ParseQuery<OfferInvite> query = ParseQuery.getQuery(OfferInvite.class);
        query.whereEqualTo("ownerUserId", ownerId);
        query.whereEqualTo("targetUserId", targetId);
        try {
            return query.getFirst();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



}
