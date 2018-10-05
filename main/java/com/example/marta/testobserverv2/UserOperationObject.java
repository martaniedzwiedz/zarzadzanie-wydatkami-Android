package com.example.marta.testobserverv2;

import android.content.Context;
import android.os.Handler;

public class UserOperationObject extends UserOperation {
    protected UserOperationObject(Context context) {
        super(context);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : mObservers) {
            observer.onUserDataChanged(mUser);
        }
    }

    public void UserData(int ID){
        mGetUserDecorator = new GetUserObject();
        mGetUserDecorator.setUser(ID);
        mGetUserDecorator.GetJson();
    }

    @Override
    public void get(int ID){
         UserData(ID);
         final Handler handler = new Handler();
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                 mUser = mGetUserDecorator.getUser();
                 notifyObservers();
             }
         }, 1200);
    }

}