package com.example.marta.testobserverv2;

import android.content.Context;
import android.os.Handler;

public class GroupOperationList extends GroupOperation {
    protected GroupOperationList(Context context) {
        super(context);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: mObservers) {
            observer.onListChange(mAndroidMapList);
        }
    }
    @Override
    public void get() {
        mGetUserDecorator = new GetRequestListDecorator(mGetUserDecorator);
        mGetUserDecorator.setRequestsList();
        mGetUserDecorator.GetJson();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAndroidMapList = mGetUserDecorator.getList();
                try {
                    mApplicationPreference.setCountRequest(mAndroidMapList.size());
                }catch(NullPointerException e){

                }
                notifyObservers();
            }
        }, 1200);
    }
}
