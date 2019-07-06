package com.vita.godealsashi.OfferActivities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.DealList;
import com.vita.godealsashi.ParseClasses.OfferInvite;
import com.vita.godealsashi.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReciveOffer extends AppCompatActivity {

    Button buttonAnswer;

    CircleImageView circleImageViewRecive;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive_offer);

        buttonAnswer = findViewById(R.id.accept_btn_recive);
        circleImageViewRecive = findViewById(R.id.circleImageReciveOffer);



        //TODO: i need to get id of sender
        final Intent intent = new Intent();
        final String ownerId = intent.getStringExtra("currentId");



        buttonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OfferInvite invite = getOffer(ownerId, targetId);

                if(invite != null){

                    DealList dealList = new DealList();
                    dealList.setOwner(invite.getOwner());
                    dealList.setTargetId(invite.getTargetId());
                    dealList.saveInBackground();

                } else {

                    Toast.makeText(ReciveOffer.this, "There's some problem with recive data", Toast.LENGTH_SHORT).show();

                }



                finish();

            }
        });


    }

    private OfferInvite getOffer(String ownerId, String targetId){
        ParseQuery<OfferInvite> query = ParseQuery.getQuery(OfferInvite.class);

        query.whereEqualTo("targetId", targetId);
        query.whereEqualTo("owner", ownerId);
        try {
            return query.getFirst();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



}
