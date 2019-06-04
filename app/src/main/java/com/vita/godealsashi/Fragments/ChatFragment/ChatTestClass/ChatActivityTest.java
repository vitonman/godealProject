package com.vita.godealsashi.Fragments.ChatFragment.ChatTestClass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vita.godealsashi.Fragments.ChatFragment.ChatActivity.ChatActivity;
import com.vita.godealsashi.ParseClasses.Message;
import com.vita.godealsashi.R;

public class ChatActivityTest extends AppCompatActivity {

    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";
    static final String TAG = "Error";

    EditText etMessage;
    Button btSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_test);


        if(ParseUser.getCurrentUser() != null){

            startWithCurrentUser();

        }

    }

    private void startWithCurrentUser(){

        setupMessagePosting();

    }



    void setupMessagePosting() {
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();

                //ParseObject message = ParseObject.create("Message");
                //message.put(Message.USER_ID_KEY, ParseUser.getCurrentUser().getObjectId());
                //message.put(Message.BODY_KEY, data);

                /*** START OF CHANGE **/

                // Using new `Message` Parse-backed model now
                Message message = new Message();
                message.setBody(data);
                message.setUserId(ParseUser.getCurrentUser().getObjectId());

                /*** END OF CHANGE **/

                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Toast.makeText(ChatActivityTest.this, "Successfully created message on Parse",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "Failed to save message", e);
                        }
                    }

                });
                etMessage.setText(null);
            }
        });
    }





}
