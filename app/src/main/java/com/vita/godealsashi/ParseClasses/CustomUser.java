package com.vita.godealsashi.ParseClasses;

import android.widget.RelativeLayout;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;


@ParseClassName("CustomUser")
public class CustomUser extends ParseObject implements Serializable {


    public CustomUser(){
        super();
    }

    public CustomUser(String body) {
        super();
    }


    public void setAge(int value){
        put("age", value);
    }

    public int getAge(){
        return getInt("age");
    }

    public void setName(String value){
        put("name", value);
    }

    public String getName(){

        return getString("name");
    }

    public String getOwnerUserId() {
        return getString("ownerUserId");
    }

    public void setOwnerUserId(String value) {
        put("ownerUserId", value);
    }

    public void setLastname(String value){
        put("lastname", value);
    }

    public String getLastname(){
        return getString("lastname");
    }



    public void setCity(String value){

        put("city", value);
    }

    public String getCity(){

        return getString("city");
    }

    public void setImage(ParseFile parseFile){

        put("image", parseFile);
    }

    public ParseFile getImage(){
        return getParseFile("image");
    }

    public void setAbility(JSONArray value){

        put("ability", value);

    }

    public JSONArray getAbility(){

        return getJSONArray("ability");
    }

    public void setUserInTouch(JSONArray value){

        put("usersintouch", value);

    }

    public JSONArray getUserIntouch(){

        return getJSONArray("usersintouch");
    }




    //RAITING SYSTEM INNED





}