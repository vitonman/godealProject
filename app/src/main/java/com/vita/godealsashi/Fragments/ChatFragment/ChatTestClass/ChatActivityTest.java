package com.vita.godealsashi.Fragments.ChatFragment.ChatTestClass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;
import com.vita.godealsashi.MainActivity;
import com.vita.godealsashi.OfferActivities.WorkOffer;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.FriendList;
import com.vita.godealsashi.ParseClasses.Message;
import com.vita.godealsashi.ParseClasses.OfferInvite;
import com.vita.godealsashi.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChatActivityTest extends AppCompatActivity {

    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";
    static final String TAG = "Error";
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 200;

    private String ownerUserId;

    EditText etMessage;
    Button btSend;
    FloatingActionButton sendOfferBtn;

    RecyclerView rvChat;
    ArrayList<Message> mMessages;

    ChatAdapter mAdapter;
    // Keep track of initial load to scroll to the bottom of the ListView
    boolean mFirstLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_test);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        final String custom_user_current_id = preferences.getString("currentUserId", "");


        if(ParseUser.getCurrentUser() != null){
            /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            final String custom_user_current_id = preferences.getString("current_ownerId", "");*/

            startWithCurrentUser();

        }



        liveQueryRefresh(custom_user_current_id);
        refreshMessages();


    }

    private void startWithCurrentUser(){


        setupMessagePosting();

    }

    private void liveQueryRefresh(String ownerUserId){
        Intent intent = getIntent();
        final String senderUserId = intent.getStringExtra("targetUserId");
        String[] users = {ownerUserId, senderUserId};

        ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();

        ParseQuery<Message> parseQuery = ParseQuery.getQuery(Message.class);
        // This query can even be more granular (i.e. only refresh if the entry was added by some other user)
        parseQuery.whereContainedIn("targetUserId", Arrays.asList(users));
        parseQuery.whereContainedIn("userId", Arrays.asList(users));

        // Connect to Parse server
        SubscriptionHandling<Message> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

        // Listen for CREATE events
        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new
                SubscriptionHandling.HandleEventCallback<Message>() {
                    @Override
                    public void onEvent(ParseQuery<Message> query, Message object) {
                        mMessages.add(0, object);

                        // RecyclerView updates need to be run on the UI thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                rvChat.scrollToPosition(0);
                            }
                        });
                    }
                });
    }



    void setupMessagePosting() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        final String custom_user_current_id = preferences.getString("currentUserId", "");

        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        rvChat = (RecyclerView) findViewById(R.id.rvChat);
        sendOfferBtn = (FloatingActionButton) findViewById(R.id.floatingBtnSendOffer);

        mMessages = new ArrayList<>();

        mFirstLoad = true;


        Intent intent = getIntent();
        ownerUserId = intent.getStringExtra("targetUserId");

        CustomUser targetUserObject = getObjectUser(ownerUserId);
        final CustomUser ownerUserObject = getObjectUser(custom_user_current_id);

        //Toast.makeText(ChatActivityTest.this, testUser.getName(), Toast.LENGTH_LONG).show();

        mAdapter = new ChatAdapter(ChatActivityTest.this, custom_user_current_id, mMessages, targetUserObject);
        rvChat.setAdapter(mAdapter);

        // associate the LayoutManager with the RecylcerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivityTest.this);
        linearLayoutManager.setReverseLayout(true);
        rvChat.setLayoutManager(linearLayoutManager);



        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();

                Message message = new Message();
                message.setBody(data);
                message.setUserId(custom_user_current_id);
                message.setTarget(ownerUserId);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(ChatActivityTest.this, "Sent",
                                Toast.LENGTH_SHORT).show();
                        if(ownerUserObject.getUserIntouch() == null){
                            final JSONArray array = new JSONArray();
                            array.put(ownerUserId);

                            ownerUserObject.setUserInTouch(array);
                            ownerUserObject.saveInBackground();


                        }else{
                            ArrayList<String> listdata = new ArrayList<String>();
                            final JSONArray jArray = ownerUserObject.getUserIntouch();
                            if (jArray != null) {
                                for (int i=0;i<jArray.length();i++){
                                    try {
                                        listdata.add(jArray.getString(i));
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                            if(!listdata.contains(ownerUserId)){

                                //nothing
                                if(jArray != null){
                                    jArray.put(ownerUserId);
                                    ownerUserObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            ownerUserObject.setUserInTouch(jArray);
                                            Toast.makeText(getApplicationContext(), "Added to created array", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                            }

                        }



                        refreshMessages();
                    }
                });
                etMessage.setText(null);
            }
        });

        sendOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OfferInvite offerInvite = getObjectInvite(custom_user_current_id, ownerUserId);

                if(offerInvite != null){

                    Toast.makeText(ChatActivityTest.this, "Already sended",
                            Toast.LENGTH_SHORT).show();

                }else{
                    Intent workOffer = new Intent(ChatActivityTest.this, WorkOffer.class);
                    workOffer.putExtra("targetUserId", ownerUserId);


                    OfferInvite offer = new OfferInvite();
                    offer.setOwnerUserId(custom_user_current_id);
                    offer.setTargetUserId(ownerUserId);
                    offer.setAcceptStatus(false);
                    offer.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            Toast.makeText(ChatActivityTest.this, "Sended",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });



                    startActivity(workOffer);
                }


            }
        });


    }


    public OfferInvite getObjectInvite(String ownerId, String targetId){

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

    public CustomUser getObjectUser(String id){
        ParseQuery<CustomUser> query = ParseQuery.getQuery(CustomUser.class);
        query.whereEqualTo("objectId", id);
        try {
            return query.getFirst();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



    private void refreshMessages(){

        Intent intent = getIntent();
        final String ownerUserId = intent.getStringExtra("targetUserId");

        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        final String custom_user_current_id = preferences.getString("currentUserId", "");

        //query.whereEqualTo("targetUserId", ParseUser.getCurrentUser().getObjectId());

        String[] users = {ownerUserId, custom_user_current_id};
        query.whereContainedIn("userId", Arrays.asList(users));
        query.whereContainedIn("targetUserId", Arrays.asList(users));
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                            rvChat.scrollToPosition(0);
                        mFirstLoad = false;
                    }
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });


    }








}