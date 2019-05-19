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

    public boolean getAccept(){

        return getBoolean("accept");

    }

    public void setAccept(boolean value){

        put("accept", value);

    }

    public void setOwneruserdata(ParseObject value){

        put("owneruserdata", value);

    }

    public ParseObject getOwneruserdata(){

        return getParseObject("owneruserdata");

    }
}
