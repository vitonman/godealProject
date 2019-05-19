package com.vita.godealsashi.registration;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.vita.godealsashi.R;

public class RegistrationActivity extends AppCompatActivity {

    private Button reg_btn, log_btn;
    private ProgressBar progressBar;


    AppCompatEditText email, password, password_confirm;
    TextInputLayout emailLayout, passwordLayout, confpasswordLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //------------------------------------------
        reg_btn = (Button) findViewById(R.id.reg_btn);
        //BUTTONS
        log_btn = (Button) findViewById(R.id.log_btn);
        //------------------------------------------

        //Text info
        //------------------------------------
        email = (AppCompatEditText) findViewById(R.id.usernameEmailText);
        password = (AppCompatEditText) findViewById(R.id.usernamePasswordText);
        password_confirm = (AppCompatEditText) findViewById(R.id.usernameConfpassText);
        //------------------------------------

        //Layouts
        //------------------------------------
        emailLayout = (TextInputLayout) findViewById(R.id.usernameEmailLayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.usernamePasswordLayout);
        confpasswordLayout = (TextInputLayout) findViewById(R.id.usernameConfpassLayout);
        //------------------------------------

        progressBar = (ProgressBar) findViewById(R.id.reg_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        //------------------------------------
        passwordLayout.setCounterEnabled(true);
        passwordLayout.setCounterMaxLength(16);
        //-------------------------------------
        confpasswordLayout.setCounterEnabled(true);
        confpasswordLayout.setCounterMaxLength(16);
        //-------------------------------------


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if(email.getText().toString().isEmpty()){
                    emailLayout.setErrorEnabled(true);
                    emailLayout.setError("Please enter your Email!");
                } else {
                    emailLayout.setErrorEnabled(false);
                }

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if(password.getText().toString().isEmpty()){
                    passwordLayout.setErrorEnabled(true);
                    passwordLayout.setError("Please enter your Password!");
                } else {
                    passwordLayout.setErrorEnabled(false);
                }

            }
        });

        password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(password.getText().toString().isEmpty()){
                    confpasswordLayout.setErrorEnabled(true);
                    confpasswordLayout.setError("Please enter your Password!");
                }else{

                    confpasswordLayout.setErrorEnabled(false);
                }

            }
        });

        registrationClicked(email, password, password_confirm);



    }

    public void registrationClicked(final AppCompatEditText email, final AppCompatEditText password, final AppCompatEditText confPass){

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userEmail = email.getText().toString();
                final String userPassword = password.getText().toString();
                final String userConfPass = confPass.getText().toString();



                progressBar.setVisibility(View.VISIBLE);

                if(!userPassword.equals(userConfPass)) {

                    confpasswordLayout.setErrorEnabled(true);
                    confpasswordLayout.setError("Passwords doesn't match!");

                }else if(!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword) && !TextUtils.isEmpty(userConfPass)){

                    // Create the ParseUser
                    ParseUser user = new ParseUser();
                    // Set core properties
                    user.setUsername(userEmail);
                    user.setPassword(userPassword);
                    user.setEmail(userEmail);
                    // Set custom properties

                    // Invoke signUpInBackground
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Hooray! Let them use the app now.

                                ParseUser.logInInBackground(userEmail, userPassword, new LogInCallback() {

                                    @Override
                                    public void done(ParseUser user, ParseException e) {

                                        if (user != null){

                                            sendToSetup();

                                        } else {

                                            Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            progressBar.setVisibility(View.INVISIBLE);

                                        }

                                    }
                                });

                            } else {
                                // Sign up didn't succeed. Look at the ParseException
                                Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                // to figure out what went wrong
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        }
                    });


                } else {

                    Toast.makeText(RegistrationActivity.this, "Check all rows, please?", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }

            }
        });

    }

    public void sendToSetup(){

        Intent mainIntent = new Intent(RegistrationActivity.this, UserSetupActivity.class);
        startActivity(mainIntent);
        finish();

    }


}
