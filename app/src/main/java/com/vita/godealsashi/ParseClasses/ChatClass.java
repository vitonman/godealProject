package com.vita.godealsashi.ParseClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("ChatClass")
public class ChatClass extends ParseObject {

    public ChatClass(){
        super();
    }

    public ChatClass(String body) {
        super();
    }


    public ParseUser getReciver() {
        return getParseUser("reciver");
    }

    public void setReciver(ParseUser value) {
        put("reciver", value);
    }


    public ParseUser getSender() {
        return getParseUser("sender");
    }

    public void setSender(ParseUser value) {
        put("sender", value);
    }

    public String getMessage(){

        return getString("message");
    }


    public void setMessage(String value){
        put("message", value);
    }

}
