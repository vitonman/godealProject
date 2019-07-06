package com.vita.godealsashi;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;
import com.vita.godealsashi.Fragments.DealFragment.rethinkDeal.DealFragmentTest;
import com.vita.godealsashi.Fragments.navigationDrawerFragments.OffersFragment.OffersFragment;
import com.vita.godealsashi.OfferActivities.ReciveOffer;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.Fragments.ChatFragment.ChatFragment;
import com.vita.godealsashi.Fragments.navigationDrawerFragments.ColleguesFragment.ColleguesFragment;
import com.vita.godealsashi.Fragments.DealFragment.DealFragment;
import com.vita.godealsashi.Fragments.ProfileFragment.ProfileFragment;
import com.vita.godealsashi.Fragments.SearchFragment.SearchFragment;
import com.vita.godealsashi.Fragments.navigationDrawerFragments.RequestsFragment.RequestFragment;
import com.vita.godealsashi.Login.LoginActivity;
import com.vita.godealsashi.ParseClasses.FriendList;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.ParseClasses.OfferInvite;
import com.vita.godealsashi.registration.RegistrationComplete;
import com.vita.godealsashi.registration.UserSetupActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar mainToolBar;
    private BottomNavigationView mainBottomNavigation;

    private LottieAnimationView loginSucess_anim;

    SharedPreferences preferences;
    ParseUser currentUser = ParseUser.getCurrentUser();

    private CustomUser userData;

    ChatFragment chatFragment;
    DealFragment dealFragment;
    ProfileFragment profileFragment;
    SearchFragment searchFragment;
    DealFragmentTest dealFragmentTest;

    //navigationdrawer fragments
    ColleguesFragment collegueFragment;
    RequestFragment requestFragment;
    OffersFragment offersFragment;

    ArrayList<String> invites;
    private List<CustomUser> user_list;

    NavigationView navigationView;
    DrawerLayout drawerLayout;

    String custom_user_current_id;
    String name;
    String lastname;
    String image;

    Set<String> friend_list;

    CustomUser user;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
        friend_list = preferences.getStringSet("friendlist", null);

        if(friend_list == null){

            Toast.makeText(MainActivity.this, "friendlist == null", Toast.LENGTH_LONG).show();
        }*/


        //activity_main or navigation_drawer
        setContentView(R.layout.navigation_drawer);

        checkFirstRun();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();


        mainToolBar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolBar);
        getSupportActionBar().setTitle("Main");
        mainBottomNavigation = findViewById(R.id.bottomNavBar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //TODO check if preferences have a data
        custom_user_current_id = preferences.getString("current_ownerId", "");


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,mainToolBar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();



        if (currentUser != null) {


            invites = new ArrayList<String>();
            //getInvites(currentUser.getObjectId());
            Toast.makeText(MainActivity.this, invites.toString(), Toast.LENGTH_LONG).show();


            // do stuff with the user

            if(custom_user_current_id.equals("")){

                checkForSetup(currentUser, editor);


            } else {


                custom_user_current_id = preferences.getString("current_ownerId", "");
                name = preferences.getString("current_name", "");
                lastname = preferences.getString("current_lastname", "");
                image = preferences.getString("current_image", "");

                // THERE STUFF FOR NAVIGATION ACCOUNT
                View hView =  navigationView.getHeaderView(0);
                TextView nav_user = (TextView)hView.findViewById(R.id.navigation_name_textview);
                CircleImageView nav_image = (CircleImageView) hView.findViewById(R.id.navigation_circle_image);
                nav_user.setText(name + " " + lastname);
                Glide.with(MainActivity.this)
                        .load(image)
                        .into(nav_image);
                //-----------------------------------
            }


            liveQueryCheckData(custom_user_current_id);

            user = new CustomUser();

            Toast.makeText(MainActivity.this, "Hello, " + currentUser.getUsername(), Toast.LENGTH_SHORT).show();
            loginSucess_anim = (LottieAnimationView) findViewById(R.id.loginsucess_anim);


            //loginSucess_anim.setVisibility(View.VISIBLE);
            loginSucess_anim.playAnimation();



            //fragments
            dealFragment = new DealFragment();
            chatFragment = new ChatFragment();
            profileFragment = new ProfileFragment();
            searchFragment = new SearchFragment();
            dealFragmentTest = new DealFragmentTest();

            //navigation fragments
            collegueFragment = new ColleguesFragment();
            requestFragment = new RequestFragment();
            offersFragment = new OffersFragment();

            replaceFragment(dealFragmentTest);


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
                            replaceFragment(dealFragmentTest);
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
                    preferences.edit().clear().apply();

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
                checkForFriends(custom_user_current_id);
                Toast.makeText(this, "Collegues", Toast.LENGTH_SHORT).show();
                replaceFragment(collegueFragment);

                break;
            case R.id.collegue_request:
                Toast.makeText(this, "collegue_request", Toast.LENGTH_SHORT).show();
                replaceFragment(requestFragment);
                break;
            case R.id.settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                replaceFragment(offersFragment);
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

    public void checkForSetup(ParseUser current_user, final SharedPreferences.Editor editor){


        if(custom_user_current_id.equals("")){

            final ParseQuery<CustomUser> queryExist = ParseQuery.getQuery(CustomUser.class);

            queryExist.whereEqualTo("owner", current_user.getObjectId());

            queryExist.getFirstInBackground(new GetCallback<CustomUser>() {
                @Override
                public void done(CustomUser object, ParseException e) {

                    if(e == null){

                        editor.putString("current_name", object.getName());
                        editor.putString("current_lastname", object.getLastname());
                        editor.putString("current_city", object.getCity());
                        editor.putString("current_age", Integer.toString(object.getAge()));
                        editor.putString("current_ownerId", object.getObjectId());
                        editor.putString("current_image", object.getImage().getUrl());

                        editor.commit();


                        // THERE STUFF FOR NAVIGATION ACCOUNT
                        View hView =  navigationView.getHeaderView(0);
                        TextView nav_user = (TextView)hView.findViewById(R.id.navigation_name_textview);
                        CircleImageView nav_image = (CircleImageView) hView.findViewById(R.id.navigation_circle_image);
                        nav_user.setText(object.getName() + " " + object.getLastname());
                        Glide.with(MainActivity.this)
                                .load(object.getImage().getUrl())
                                .into(nav_image);
                        //-----------------------------------

                        Toast.makeText(MainActivity.this, "Added to shared preferences", Toast.LENGTH_SHORT).show();



                    } else {

                        Toast.makeText(MainActivity.this, "You must finish registration.", Toast.LENGTH_SHORT).show();
                        Intent toSetupPage = new Intent(MainActivity.this, UserSetupActivity.class);
                        startActivity(toSetupPage);

                    }

                }
            });

        } else {

            Toast.makeText(MainActivity.this, "Your user is fine.", Toast.LENGTH_SHORT).show();

        }




    }


    private void checkForFriends(String custom_user_current_id){


        ParseQuery<FriendList> query = ParseQuery.getQuery(FriendList.class);

        query.whereEqualTo("owner", custom_user_current_id);

        query.findInBackground(new FindCallback<FriendList>() {
            @Override
            public void done(List<FriendList> objects, ParseException e) {

                if (e == null){

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    SharedPreferences.Editor editor = preferences.edit();

                    friend_list = new HashSet<>();

                    for (FriendList object: objects){

                        friend_list.add(object.getTargetId());

                    }

                    editor.putStringSet("friendlist", friend_list);
                    editor.apply();

                }



            }
        });



    }


    //the big boss query class __________________________

    private void liveQueryCheckData(final String custom_user_current_id){

        //FRIENDLIST QUERY
        ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
        ParseQuery<FriendList> parseLiveQueryFriendlist = ParseQuery.getQuery(FriendList.class);
        parseLiveQueryFriendlist.whereEqualTo("targetId", custom_user_current_id);
        SubscriptionHandling<FriendList> friendListSubscriptionHandling = parseLiveQueryClient.subscribe(parseLiveQueryFriendlist);

        friendListSubscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<FriendList>() {
            @Override
            public void onEvent(ParseQuery<FriendList> query, FriendList object) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {

                        //DO SOME UPDATE HERE

                        Toast.makeText(getApplicationContext(), "Added friend", Toast.LENGTH_SHORT).show();

                    }

                });
            }
        });

        //INVITE TO FRIEND QUERY

        ParseQuery<Invite> parseQueryInviteFriend = ParseQuery.getQuery(Invite.class);
        parseQueryInviteFriend.whereEqualTo("targetId", custom_user_current_id);
        SubscriptionHandling<Invite> inviteFriendListSubscriptionHandling = parseLiveQueryClient.subscribe(parseQueryInviteFriend);

        inviteFriendListSubscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<Invite>() {
            @Override
            public void onEvent(ParseQuery<Invite> query, Invite object) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {

                        Toast.makeText(getApplicationContext(), "You have been invited to friend", Toast.LENGTH_SHORT).show();

                    }

                });
            }
        });


        //OFFER INVITE QUERY
        ParseQuery<OfferInvite> parseQueryOfferInvite = ParseQuery.getQuery(OfferInvite.class);
        parseQueryOfferInvite.whereEqualTo("targetId", custom_user_current_id);
        SubscriptionHandling<OfferInvite> subscriptionHandling = parseLiveQueryClient.subscribe(parseQueryOfferInvite);

        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<OfferInvite>() {
            @Override
            public void onEvent(ParseQuery<OfferInvite> query, OfferInvite object) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {

                        //showEditDialog();
                        Intent toReciveOffer = new Intent(MainActivity.this, ReciveOffer.class);
                        toReciveOffer.putExtra("currentId", custom_user_current_id);
                        startActivity(toReciveOffer);


                        Toast.makeText(MainActivity.this, "OFFER INVITE", Toast.LENGTH_SHORT).show();

                    }

                });
            }
        });
    }

    //-----------------------------------------------------------------



    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)
            Intent intent = new Intent(MainActivity.this, RegistrationComplete.class);
            startActivity(intent);
            finish();

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
            Toast.makeText(MainActivity.this, "The program has been updated", Toast.LENGTH_SHORT).show();

        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }


}
