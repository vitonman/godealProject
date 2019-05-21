package com.vita.godealsashi.ParseClasses;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

@ParseClassName("FriendList")
public class FriendList extends ParseObject {

    public FriendList(){
        super();
    }

    public FriendList(String body) {
        super();
    }




    //RAITING SYSTEM INNED

    public ParseUser getTarget() {
        return getParseUser("objectid");
    }

    public void setTarget(ParseUser value) {
        put("target", value);
    }


    public ParseUser getOwner() {
        return getParseUser("owner");
    }

    public void setOwner(ParseUser value) {
        put("owner", value);
    }

    public void setFriend(String value){

        put("friendid", value);

    }

    public String getFriend(){

        return getString("friendid");

    }


}
