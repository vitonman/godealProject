package com.vita.godealsashi.User;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

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







    public String getOwner() {
        return getString("owner");
    }

    public void setOwner(String value) {
        put("owner", value);
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


}