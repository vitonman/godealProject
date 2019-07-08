package com.vita.godealsashi.ParseClasses;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

@ParseClassName("Invite")
public class Invite extends ParseObject {


    public static final String USER_ID_KEY = "ownerUserId";
    public static final String TARGET_USER_ID = "targetUserId";

    public Invite(){
        super();
    }

    public Invite(String body) {
        super();
    }




    //RAITING SYSTEM INNED


    public String getTargetUserId() {
        return getString(TARGET_USER_ID);
    }

    public void setTargetUserId(String value) {
        put(TARGET_USER_ID, value);
    }


    public String getOwnerUserId() {
        return getString(USER_ID_KEY);
    }

    public void setOwnerUserId(String  value) {
        put(USER_ID_KEY, value);
    }

    public boolean getAccept(){

        return getBoolean("accept");

    }

    public void setAccept(boolean value){

        put("accept", value);

    }

}
