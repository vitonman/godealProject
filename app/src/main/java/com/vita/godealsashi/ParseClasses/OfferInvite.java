package com.vita.godealsashi.ParseClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("OfferInvite")
public class OfferInvite extends ParseObject {

    public static final String USER_ID_KEY = "ownerUserId";
    public static final String TARGET_USER_ID = "targetUserId";

    public String getTargetUserId() {
        return getString(TARGET_USER_ID);
    }

    public void setTargetUserId(String value) {
        put(TARGET_USER_ID, value);
    }


    public String getOwnerUserId() {
        return getString(USER_ID_KEY);
    }

    public void setOwnerUserId(String  value) {
        put(USER_ID_KEY, value);
    }

    public void setAcceptStatus(Boolean value) {

        put("acceptStatus", value);

    }

    public Boolean getAcceptStatus(){

        return getBoolean("acceptStatus");

    }



}
