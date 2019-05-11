package com.vita.godealsashi.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.vita.godealsashi.MainActivity;
import com.vita.godealsashi.R;
import com.vita.godealsashi.registration.RegistrationActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText username_text, password_text;
    private Button log_btn, log_reg_btn;

    private LottieAnimationView loginSucess_anim, loading_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_text = (EditText) findViewById(R.id.log_user_name);
        password_text = (EditText) findViewById(R.id.log_user_pass);

        log_btn = (Button) findViewById(R.id.log_btn);
        log_reg_btn = (Button) findViewById(R.id.log_reg_btn);

        loading_anim = (LottieAnimationView) findViewById(R.id.loading_anim);

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading_anim.setRepeatCount(1);
                loading_anim.playAnimation();
                String username = username_text.getText().toString();
                String password = password_text.getText().toString();

                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){

                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if(user != null){

                                sendToMain();

                            } else {

                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                //loginSucess_anim.setVisibility(View.INVISIBLE);
                            }

                        }
                    });

                } else {

                    Toast.makeText(LoginActivity.this, "Check password or username", Toast.LENGTH_SHORT).show();

                }

            }
        });

        log_reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void sendToMain(){

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}
