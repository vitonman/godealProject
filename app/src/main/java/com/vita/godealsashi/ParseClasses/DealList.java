package com.vita.godealsashi.ParseClasses;


import com.parse.ParseClassName;
import com.parse.ParseObject;


@ParseClassName("DealList")
public class DealList extends ParseObject {

    public DealList(){
        super();
    }

    public DealList(String body) {
        super();
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



}
