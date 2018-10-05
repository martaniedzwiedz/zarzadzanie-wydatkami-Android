package com.example.marta.testobserverv2;

import android.content.Context;
import android.os.Handler;

public class GroupOperationSum extends GroupOperation {
    protected GroupOperationSum(Context context) {
        super(context);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : mObservers) {
            observer.onSumChange(sum);
        }
    }

    @Override
    public void get() {
        mGetUserDecorator = new GetSumListDecorator(mGetUserDecorator);
        mGetUserDecorator.setSum();
        mGetUserDecorator.GetJson();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sum = mGetUserDecorator.getSum();
                notifyObservers();
            }
        }, 1200);
    }
}
