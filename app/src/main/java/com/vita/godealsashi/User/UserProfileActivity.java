package com.vita.godealsashi.User;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.vita.godealsashi.R;

public class UserProfileActivity extends AppCompatActivity {




    ConstraintLayout constraintLayout;

    AppCompatEditText username, password;
    TextInputLayout userLayout, passwordLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

 /*       constraintLayout = findViewById(R.id.activity_user_pofile);
        constraintLayout.setOnClickListener(null);

        *//*layout_test = findViewById(R.id.text1);
        layout_test.setError("@");
*//*
        username = (AppCompatEditText) findViewById(R.id.usernameEditText);
        password = (AppCompatEditText) findViewById(R.id.passwordEditText);

        userLayout = (TextInputLayout) findViewById(R.id.usernameInputlayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordInputLayout);*/
/*
        userLayout.setCounterEnabled(true);
        userLayout.setCounterMaxLength(12);

        passwordLayout.setCounterEnabled(true);
        passwordLayout.setCounterMaxLength(16);




        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(username.getText().toString().isEmpty()){
                    userLayout.setErrorEnabled(true);
                    userLayout.setError("Please enter your username!");
                } else {
                    userLayout.setErrorEnabled(false);
                }

            }
        });*/
/*
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(username.getText().toString().isEmpty()){
                    userLayout.setErrorEnabled(true);
                    userLayout.setError("Please enter your username!");
                } else {
                    userLayout.setErrorEnabled(false);
                }
            }
        });*/



    }
}
