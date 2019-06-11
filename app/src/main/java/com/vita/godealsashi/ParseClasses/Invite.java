package com.vita.godealsashi.ParseClasses;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

@ParseClassName("Invite")
public class Invite extends ParseObject {

    public Invite(){
        super();
    }

    public Invite(String body) {
        super();
    }




    //RAITING SYSTEM INNED

    public ParseUser getTarget() {
        return getParseUser("target");
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

    public void setOwner(String  value) {
        put("owner", value);
    }

    public boolean getAccept(){

        return getBoolean("accept");

    }

    public void setAccept(boolean value){

        put("accept", value);

    }

}
