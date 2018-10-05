package com.example.marta.testobserverv2;

import android.content.Context;
import android.os.Handler;

public class UserOperationList extends UserOperation {
    protected UserOperationList(Context context) {
        super(context);
    }
    @Override
    public void notifyObservers() {
        for (Observer observer: mObservers) {
            observer.onListChange(mAndroidMapList);
        }
    }
    @Override
    public void get(int ID){
        mGetUserDecorator = new GetUserListDecorator(mGetUserDecorator);
        mGetUserDecorator.setUsersList();
        mGetUserDecorator.GetJson();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAndroidMapList = mGetUserDecorator.getList();
                notifyObservers();
            }
        }, 1200);
    }
}
