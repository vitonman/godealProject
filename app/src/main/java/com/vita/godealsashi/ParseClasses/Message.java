package com.vita.godealsashi.ParseClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

@ParseClassName("Message")
public class Message extends ParseObject implements IMessage {
    public static final String USER_ID_KEY = "userId";
    public static final String BODY_KEY = "body";
    public static final String TARGET_USER_ID = "targetUserId";

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getBody() {
        return getString(BODY_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setBody(String body) {
        put(BODY_KEY, body);
    }

    public void setTarget(String targetUserId) {
        put(TARGET_USER_ID, targetUserId);
    }

    public String getTarget() {
        return getString(TARGET_USER_ID);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public IUser getUser() {
        return null;
    }
}