package com.vita.godealsashi.registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.R;

import org.json.JSONArray;

public class AbilityActivity extends AppCompatActivity {

    private ImageView activity_driver, activity_worker, activity_teacher;

    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability);

        final ParseUser currentUser = ParseUser.getCurrentUser();

        activity_driver = (ImageView) findViewById(R.id.user_driver_pick);
        activity_teacher = (ImageView) findViewById(R.id.user_teacher_pick);
        activity_worker = (ImageView) findViewById(R.id.user_worker_pick);

        saveBtn = (Button) findViewById(R.id.saveBtn);

        final JSONArray ability = new JSONArray();


        activity_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability.put("driver");

                activity_driver.setVisibility(View.INVISIBLE);
            }
        });

        activity_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ability.put("worker");
                //exist
                activity_worker.setVisibility(View.INVISIBLE);
            }
        });

        activity_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ability.put("teacher");

                activity_teacher.setVisibility(View.INVISIBLE);
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);
                queryExist.whereEqualTo("owner", currentUser);
                queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
                    @Override
                    public void done(CustomUser object, ParseException e) {
                        if(e == null){

                            object.setAbility(ability);
                            object.saveInBackground();
                            Toast.makeText(AbilityActivity.this, "Changed!", Toast.LENGTH_SHORT).show();

                        } else {


                            Toast.makeText(AbilityActivity.this, "First add step one!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }

        });


    }
}
