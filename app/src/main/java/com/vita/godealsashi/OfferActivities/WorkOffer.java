package com.vita.godealsashi.OfferActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;
import com.vita.godealsashi.MainActivity;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.Message;
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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String currentUserId = preferences.getString("currentUserId", "");

        Intent intent = getIntent();
        String targetUserId = intent.getStringExtra("targetUserId");

        Toast.makeText(WorkOffer.this, targetUserId, Toast.LENGTH_SHORT).show();

        //OfferInvite invite = getOffer(currentUserId, targetId);

        liveQueryCheckForOffer(targetUserId, currentUserId);
    }

    public void liveQueryCheckForOffer(String targetUserId, String currentUserId){


        ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();

        ParseQuery<OfferInvite> parseQueryOfferInvite = ParseQuery.getQuery(OfferInvite.class);

        parseQueryOfferInvite.whereContains("ownerUserId", currentUserId);
        parseQueryOfferInvite.whereContains("targetUserId", targetUserId);


        SubscriptionHandling<OfferInvite> subscriptionHandling = parseLiveQueryClient.subscribe(parseQueryOfferInvite);
        subscriptionHandling.handleEvent(SubscriptionHandling.Event.UPDATE, new SubscriptionHandling.HandleEventCallback<OfferInvite>() {
            @Override
            public void onEvent(ParseQuery<OfferInvite> query, final OfferInvite object) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {

                        if(object.getAcceptStatus().equals(true)){

                            progressBarStatus.setVisibility(View.INVISIBLE);
                            statusTextView.setText("User ACCEPTED OFFER!");


                        } else {



                        }


                    }

                });
            }
        });
    }





}
