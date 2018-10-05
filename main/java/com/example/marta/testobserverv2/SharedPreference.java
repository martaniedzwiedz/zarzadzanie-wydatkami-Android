package com.example.marta.testobserverv2;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static SharedPreference INSTANCE = null;
    private String userStatus;
    private int GroupID;
    private int userID;
    private String userName;
    private String token;
    private boolean userSentRequest;
    private int countRequest = 0;


    public int getCountRequest() {
        return countRequest;
    }
    public void setCountRequest(int countRequest) {
        this.countRequest = countRequest;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public boolean isUserSentRequest() {
        return userSentRequest;
    }
    public void setUserSentRequest(boolean userSentRequest) {this.userSentRequest = userSentRequest;}
    public String getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    public int getGroupID() {
        return GroupID;
    }
    public void setGroupID(int groupID) {
        GroupID = groupID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public static SharedPreference getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SharedPreference();
        }
        return INSTANCE;
    }
}
