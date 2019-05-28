package com.vita.godealsashi.Fragments.ChatFragment.ChatActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;
import com.vita.godealsashi.Fragments.SearchFragment.WorkPost;
import com.vita.godealsashi.Fragments.SearchFragment.WorkPostRecycleAdapter;
import com.vita.godealsashi.MainActivity;
import com.vita.godealsashi.ParseClasses.ChatClass;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ImageView send_message_btn;
    private TextView text_message_to_send;
    private RecyclerView recyclerView;
    private ChatRecycleActivityAdapter chatRecycleActivityAdapter;

    private List<ChatClass> message_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        send_message_btn = findViewById(R.id.send_text_btn);
        text_message_to_send = findViewById(R.id.text_edit_mesage);


        message_list = new ArrayList<>();
        recyclerView = findViewById(R.id.chat_recycle_view);

        chatRecycleActivityAdapter = new ChatRecycleActivityAdapter(message_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chatRecycleActivityAdapter);


        final ParseUser currentUser = ParseUser.getCurrentUser();
        Intent get = getIntent();
        final ParseUser ownerUser = get.getParcelableExtra("OwnerParseUser");

        ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
        ParseQuery<ChatClass> parseQuery = ParseQuery.getQuery(ChatClass.class);
        parseQuery.whereEqualTo("sender", currentUser);

        SubscriptionHandling<ChatClass> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<ChatClass>() {
            @Override
            public void onEvent(ParseQuery<ChatClass> query, final ChatClass object) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {

                        Toast.makeText(ChatActivity.this, object.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String message = text_message_to_send.getText().toString();

                if(!TextUtils.isEmpty(message)){


                    sendInput(message, currentUser, ownerUser);

                } else {

                    Toast.makeText(ChatActivity.this, "The text is empty or something wrong", Toast.LENGTH_SHORT).show();

                }

            }
        });


        getData(currentUser, ownerUser);
    }


    public void sendInput(String message, ParseUser current_user, ParseUser object_user) {

        ChatClass newMessage = new ChatClass();

        newMessage.setSender(current_user);
        newMessage.setReciver(object_user);
        newMessage.setMessage(message);
        newMessage.saveInBackground();

    }

    public void getData(ParseUser current_user, ParseUser object_user){

        ParseQuery<ChatClass> query = ParseQuery.getQuery(ChatClass.class);
        //query.whereContains("ability", ability_spinner.getSelectedItem().toString());
        query.findInBackground(new FindCallback<ChatClass>() {
            @Override
            public void done(List<ChatClass> results, ParseException e) {

                if(e == null){

                    //progressBar.setVisibility(View.INVISIBLE);

                    for (ChatClass r: results){
                        // Do whatever you want with the data...
                        if(r != null){

                            message_list.add(r);

                        } else {

                            // something went wrong


                        }



                    }

                    chatRecycleActivityAdapter.notifyDataSetChanged();

                } else {

                    //progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(ChatActivity.this, "Something wrong", Toast.LENGTH_LONG).show();

                }


            }
        });

    }

}
