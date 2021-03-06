package com.vita.godealsashi.registration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.MainActivity;
import com.vita.godealsashi.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSetupActivity extends AppCompatActivity {

    private EditText age_text_edit, name_text_edit, lastname_text_edit;
    private Spinner city_spinner;

    private File image;



    private TextView city_text_view;

    private CircleImageView setupImage;
    private Uri mainImageURI = null;

    private LoaderTextView loaderTextView;

    private Button saveDataBtn;
    private Button stepTwoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setup);



        final ParseUser currentUser = ParseUser.getCurrentUser();

        age_text_edit = (EditText) findViewById(R.id.user_age_edit);
        name_text_edit = (EditText) findViewById(R.id.user_message);
        lastname_text_edit = (EditText) findViewById(R.id.user_city_text);

        setupImage = (CircleImageView) findViewById(R.id.setup_image);
        Glide.with(UserSetupActivity.this)
                .load(R.mipmap.ic_person)
                .into(setupImage);



        saveDataBtn = (Button) findViewById(R.id.save_data_user);
        stepTwoBtn = (Button) findViewById(R.id.stepTwoBtn);


        //************SPINER*****************
        city_text_view = (TextView) findViewById(R.id.city_textView);


        //get the spinner from the xml.
        city_spinner = (Spinner) findViewById(R.id.user_city_spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"Tallinn", "Tartu", "Pärnu", "Rakvere"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(UserSetupActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        city_spinner.setAdapter(adapter);
        //**********SPINNER******************


        if (currentUser != null) {
            // do stuff with the user

            saveDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userName = name_text_edit.getText().toString();
                    String userAge = age_text_edit.getText().toString();
                    String userLastname = lastname_text_edit.getText().toString();
                    String userCity = city_spinner.getSelectedItem().toString();

                    // age = Integer.parseInt(age_text.getText().toString());
                    //Number age2 = NumberFormat.getInstance().parse("int NUmber");
                    if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userAge) && mainImageURI!= null && !TextUtils.isEmpty(userLastname)
                    && !TextUtils.isEmpty(userCity)){

                        File image = new File(mainImageURI.getPath());
                        ParseFile parseFile = new ParseFile(image);

                        setUserData(currentUser.getObjectId(), userName, userLastname, userCity, userAge, parseFile);


                    } else if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userAge) && mainImageURI!= null && !TextUtils.isEmpty(userLastname)
                            && !TextUtils.isEmpty(userCity)){

                        Toast.makeText(UserSetupActivity.this, "Please select all field", Toast.LENGTH_SHORT).show();

                    }

                }
            });

            setupImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                        if(ContextCompat.checkSelfPermission(UserSetupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                            Toast.makeText(UserSetupActivity.this, "Permision Denied",
                                    Toast.LENGTH_SHORT).show();

                            ActivityCompat.requestPermissions(UserSetupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                        }else{
                            BringImagePicker();
                        }

                    } else {
                        BringImagePicker();
                    }

                }
            });

            stepTwoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserSetupActivity.this, AbilityActivity.class);
                    startActivity(intent);
                }
            });


            getUserData(currentUser.getObjectId());


        } else {
            // show the signup or login screen
            Toast.makeText(UserSetupActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
        }



    }



    private void getUserData(String currentUser){

        final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);
        queryExist.whereEqualTo("ownerUserId", currentUser);
        queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
            @Override
            public void done(CustomUser object, ParseException e) {

                if(e == null){
                    String name = object.getName();
                    int age = object.getAge();
                    String lastname = object.getLastname();
                    String city = object.getCity();

                    String objectId = object.getObjectId();


                    age_text_edit.setText(Integer.toString(age));
                    name_text_edit.setText(name);
                    lastname_text_edit.setText(lastname);
                    city_text_view.setText(city);


                    /*RequestOptions placeholderRequest = new RequestOptions();
                    placeholderRequest.placeholder(R.mipmap.ic_person);*/

                    Glide.with(UserSetupActivity.this)
                            //.setDefaultRequestOptions(placeholderRequest)
                            .load(object.getImage().getUrl())
                            .placeholder(R.mipmap.ic_person)
                            .into(setupImage);

                    Toast.makeText(UserSetupActivity.this, "Data exist", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(UserSetupActivity.this, "No data", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    private void setUserData(final String ownerUserId, final String name, final String lastname,
                             final String city, final String age,  final ParseFile image){


        final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);
        queryExist.whereEqualTo("ownerUserId", ownerUserId);
        queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
            @Override
            public void done(final CustomUser object, ParseException e) {
                if(e == null){

                    object.setName(name);
                    object.setLastname(lastname);
                    object.setAge(Integer.parseInt(age));
                    object.setCity(city);
                    object.setImage(image);
                    object.saveInBackground();


                    Toast.makeText(UserSetupActivity.this, "Changed!", Toast.LENGTH_SHORT).show();

                } else if (object == null){

                    final CustomUser user = new CustomUser();
                    user.setName(name);
                    user.setLastname(lastname);
                    user.setAge(Integer.parseInt(age));
                    user.setImage(image);
                    user.setOwnerUserId(ownerUserId);
                    user.setCity(city);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e == null){


                            } else {

                                Toast.makeText(UserSetupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }



                        }
                    });


                } else {

                    Toast.makeText(UserSetupActivity.this, "Wrong ! " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    private void BringImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(UserSetupActivity.this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageURI = result.getUri();
                setupImage.setImageURI(mainImageURI);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }

    public void toMain(){

        Intent intent = new Intent(UserSetupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }




}
