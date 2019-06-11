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

    public String getTargetId() {
        return getString("targetId");
    }

    public void setTargetId(String value) {
        put("targetId", value);
    }



    public String getOwner() {
        return getString("owner");
    }

    public void setOwner(String value) {
        put("owner", value);
    }

    public void setFriend(String value){

        put("friendid", value);

    }

    public String getFriend(){

        return getString("friendid");

    }


}
