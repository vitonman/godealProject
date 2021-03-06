package com.vita.godealsashi.parse;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseObject;


import com.vita.godealsashi.MainActivity;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.Fragments.SearchFragment.WorkPost;
import com.vita.godealsashi.ParseClasses.FriendList;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.ParseClasses.Message;
import com.vita.godealsashi.ParseClasses.OfferInvite;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Godeal extends Application {

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax

        ParseObject.registerSubclass(CustomUser.class);
        ParseObject.registerSubclass(WorkPost.class);
        ParseObject.registerSubclass(Invite.class);
        ParseObject.registerSubclass(FriendList.class);
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(OfferInvite.class);


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("e9qhz1lugzfL1PySRkB5dpyOTUBrEBAQPKcKRMMQ")
                // if desired
                .clientKey("MsbvxOY6Ul53FtoXVBLtZ5D5iQUKQFBMQkcMEMBK")
                .server("https://pg-app-7v8inl3xrr16y2f9hebc2fbdere01c.scalabl.cloud/1/")
                .enableLocalDataStore()
                .build()
        );



    }


}
