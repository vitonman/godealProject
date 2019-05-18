package com.vita.godealsashi;

import android.animation.Animator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.Fragments.ChatFragment.ChatFragment;
import com.vita.godealsashi.Fragments.navigationDrawerFragments.ColleguesFragment.ColleguesFragment;
import com.vita.godealsashi.Fragments.DealFragment.DealFragment;
import com.vita.godealsashi.Fragments.ProfileFragment.ProfileFragment;
import com.vita.godealsashi.Fragments.SearchFragment.SearchFragment;
import com.vita.godealsashi.Login.LoginActivity;
import com.vita.godealsashi.registration.UserSetupActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar mainToolBar;
    private BottomNavigationView mainBottomNavigation;

    private LottieAnimationView loginSucess_anim;



    ChatFragment chatFragment;
    DealFragment dealFragment;
    ProfileFragment profileFragment;
    SearchFragment searchFragment;

    //navigationdrawer fragments
    ColleguesFragment collegueFragment;


    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //activity_main or navigation_drawer
        setContentView(R.layout.navigation_drawer);

        ParseUser currentUser = ParseUser.getCurrentUser();



        mainToolBar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolBar);
        getSupportActionBar().setTitle("Main");

        mainBottomNavigation = findViewById(R.id.bottomNavBar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,mainToolBar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();



        if (currentUser != null) {
            // do stuff with the user
            checkForSetup(currentUser);

            Toast.makeText(MainActivity.this, "Hello, " + currentUser.getUsername(), Toast.LENGTH_SHORT).show();
            loginSucess_anim = (LottieAnimationView) findViewById(R.id.loginsucess_anim);


            //loginSucess_anim.setVisibility(View.VISIBLE);
            loginSucess_anim.playAnimation();

            //fragments
            dealFragment = new DealFragment();
            chatFragment = new ChatFragment();
            profileFragment = new ProfileFragment();
            searchFragment = new SearchFragment();

            //navigation fragments
            collegueFragment = new ColleguesFragment();



            mainBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {

                        case R.id.bottom_action_search:
                            replaceFragment(searchFragment);
                            return true;
                        case R.id.bottom_action_chat:
                            replaceFragment(chatFragment);
                            return true;
                        case R.id.bottom_action_deals:
                            replaceFragment(dealFragment);
                            return true;
                        case R.id.bottom_action_account:
                            replaceFragment(profileFragment);
                            return true;

                        default:
                            return false;

                    }

                }
            });


            loginSucess_anim.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    loginSucess_anim.setVisibility(View.INVISIBLE);


                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            // show the signup or login screen
            Toast.makeText(MainActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
            sendToLogIn();
        }

    }



    private void sendToLogIn(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.log_out:

                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    // do stuff with the user
                    Toast.makeText(MainActivity.this, "Problem with log out", Toast.LENGTH_SHORT).show();

                } else {
                    // show the signup or login screen
                    Toast.makeText(MainActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
                    sendToLogIn();
                }

                return true;

            default:
                return false;

        }


    }



    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id){

            case R.id.collegues:
                Toast.makeText(this, "Collegues", Toast.LENGTH_SHORT).show();
                replaceFragment(collegueFragment);

                break;
            case R.id.collegue_request:
                Toast.makeText(this, "collegue_request", Toast.LENGTH_SHORT).show();
                //replaceFragment(requestFragment);
                break;
            case R.id.settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.history:
                Toast.makeText(this, "history", Toast.LENGTH_SHORT).show();
                break;
            case R.id.donate:
                Toast.makeText(this, "donate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.updates:
                Toast.makeText(this, "updates", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bug_report:
                Toast.makeText(this, "bug_report", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);

        } else{

            super.onBackPressed();

        }

    }

    public void checkForSetup(ParseUser current_user){

        final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);

        queryExist.whereEqualTo("owner", current_user);

        queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
            @Override
            public void done(CustomUser object, ParseException e) {

                if(e == null){

                    Toast.makeText(MainActivity.this, "Your user is fine.", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(MainActivity.this, "You must finish registration.", Toast.LENGTH_SHORT).show();
                    Intent toSetupPage = new Intent(MainActivity.this, UserSetupActivity.class);
                    startActivity(toSetupPage);

                }

            }
        });

    }
}
