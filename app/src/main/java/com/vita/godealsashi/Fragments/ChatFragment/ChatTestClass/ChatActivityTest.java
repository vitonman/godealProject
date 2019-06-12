package com.vita.godealsashi.Fragments.ChatFragment.ChatTestClass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.Message;
import com.vita.godealsashi.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class ChatActivityTest extends AppCompatActivity {

    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";
    static final String TAG = "Error";
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 200;


    EditText etMessage;
    Button btSend;

    RecyclerView rvChat;
    ArrayList<Message> mMessages;
    ArrayList<CustomUser> usersList;
    ChatAdapter mAdapter;
    // Keep track of initial load to scroll to the bottom of the ListView
    boolean mFirstLoad;

    // Create a handler which can run code periodically
    static final int POLL_INTERVAL = 1000; // milliseconds
    Handler myHandler = new Handler();  // android.os.Handler
    Runnable mRefreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            myHandler.postDelayed(this, POLL_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_test);


        if(ParseUser.getCurrentUser() != null){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

            final String custom_user_current_id = preferences.getString("current_ownerId", "");

            startWithCurrentUser();

        }
        myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);


    }

    private void startWithCurrentUser(){

        setupMessagePosting();
        readUserInfo();
    }



    void setupMessagePosting() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        final String custom_user_current_id = preferences.getString("current_ownerId", "");

        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        rvChat = (RecyclerView) findViewById(R.id.rvChat);
        mMessages = new ArrayList<>();
        usersList = new ArrayList<>();
        mFirstLoad = true;
        final String userId = custom_user_current_id;
        mAdapter = new ChatAdapter(ChatActivityTest.this, userId, mMessages, usersList);
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

                Intent intent = getIntent();
                final String ownerUserId = intent.getStringExtra("targetUserId");

                Message message = new Message();
                message.setBody(data);
                message.setUserId(userId);
                message.setTarget(ownerUserId);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(ChatActivityTest.this, "Successfully created message on Parse",
                                Toast.LENGTH_SHORT).show();
                        refreshMessages();
                    }
                });
                etMessage.setText(null);
            }
        });

    }

    private void readUserInfo(){


        Intent intent = getIntent();
        final String ownerUserId = intent.getStringExtra("targetUserId");
        // Construct query to execute
        ParseQuery<CustomUser> query = ParseQuery.getQuery(CustomUser.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        final String custom_user_current_id = preferences.getString("current_ownerId", "");

        //query.whereEqualTo("targetUserId", ParseUser.getCurrentUser().getObjectId());

        String[] users = {ownerUserId, custom_user_current_id};
        query.whereContainedIn("objectId", Arrays.asList(users));
        query.findInBackground(new FindCallback<CustomUser>() {
            public void done(List<CustomUser> customUsersList, ParseException e) {
                if (e == null) {
                    usersList.clear();
                    usersList.addAll(customUsersList);
                    mAdapter.notifyDataSetChanged(); // update adapter

                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });

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

        final String custom_user_current_id = preferences.getString("current_ownerId", "");

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
