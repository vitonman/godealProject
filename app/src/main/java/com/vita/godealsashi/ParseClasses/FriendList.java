package com.vita.godealsashi.ParseClasses;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

@ParseClassName("FriendList")
public class FriendList extends ParseObject {


    public static final String USER_ID_KEY = "ownerUserId";
    public static final String TARGET_USER_ID = "targetUserId";

    public FriendList(){
        super();
    }

    public FriendList(String body) {
        super();
    }




    //TODO: Rating SYSTEM INNED

    public String getTargetUserId() {
        return getString(TARGET_USER_ID);
    }

    public void setTargetUserId(String value) {
        put(TARGET_USER_ID, value);
    }



    public String getOwnerUserId() {
        return getString(USER_ID_KEY);
    }

    public void setOwner(String value) {
        put(USER_ID_KEY, value);
    }

    public void setFriend(String value){

        put("friendid", value);

    }

    public String getFriend(){

        return getString("friendid");

    }


}
