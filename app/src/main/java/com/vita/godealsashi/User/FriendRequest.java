package com.vita.godealsashi.User;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.vita.godealsashi.CustomClasses.CustomUser;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;


@ParseClassName("FriendRequest")
public class FriendRequest extends ParseObject {


    public FriendRequest(){
        super();
    }

    public FriendRequest(String body) {
        super();
    }



    //-----------------sentREQUEST------------------
    public void setSent(JSONArray value){

        put("sent", value);
    }

    public JSONArray getSent(){

        return getJSONArray("sent");
    }
    //-----------------sentREQUEST------------------



    //-----------------recivedREQUEST------------------
    public void setRecived(JSONArray value){

        put("recived", value);

    }
    public JSONArray getRecived(){

        return getJSONArray("recived");
    }
    //-----------------recivedREQUEST------------------


    //-----------------friendList------------------
    public void setFriendlist(JSONArray value){

        put("friendlist", value);

    }

    public JSONArray getFriendlist(){

        return getJSONArray("friendlist");
    }
    //-----------------friendList------------------

    //RAITING SYSTEM INNED

    public String getObjectid() {
        return getString("objectid");
    }

    public void setObjectid(String value) {
        put("objectid", value);
    }


    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser value) {
        put("user", value);
    }

}