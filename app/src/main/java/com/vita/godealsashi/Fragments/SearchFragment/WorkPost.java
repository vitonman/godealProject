package com.vita.godealsashi.Fragments.SearchFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


@ParseClassName("WorkPost")
public class WorkPost extends ParseObject {


    public WorkPost(){
        super();
    }

    public WorkPost(String body) {
        super();
    }



    public void setPost(String value){
        put("name", value);
    }

    public String getPost(){

        return getString("name");
    }

    public void setDescription(String value){

        put("description", value);

    }

    public String getDescription(){
        return getString("description");
    }

    @Override
    public Date getCreatedAt() {
        return super.getCreatedAt();
    }

    public void setAbility(JSONArray value){
        put("ability", value);
    }

    public JSONArray getAbility(){

        return getJSONArray("ability");
    }

    public ParseUser getOwner() {
        return getParseUser("owner");
    }

    public void setOwner(ParseUser value) {
        put("owner", value);
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



}