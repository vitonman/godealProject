package com.vita.godealsashi.ParseClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("OfferInvite")
public class OfferInvite extends ParseObject {

    public static final String USER_ID_KEY = "owner";
    public static final String TARGET_USER_ID = "targetId";

    public String getTargetId() {
        return getString(TARGET_USER_ID);
    }

    public void setTargetId(String value) {
        put(TARGET_USER_ID, value);
    }


    public String getOwner() {
        return getString(USER_ID_KEY);
    }

    public void setOwner(String  value) {
        put(USER_ID_KEY, value);
    }



}
