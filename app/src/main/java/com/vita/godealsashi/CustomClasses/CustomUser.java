package com.vita.godealsashi.CustomClasses;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;


@ParseClassName("CustomUser")
public class CustomUser extends ParseObject {


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

    public ParseUser getOwner() {
        return getParseUser("owner");
    }

    public void setOwner(ParseUser value) {
        put("owner", value);
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


    //RAITING SYSTEM INNED



}