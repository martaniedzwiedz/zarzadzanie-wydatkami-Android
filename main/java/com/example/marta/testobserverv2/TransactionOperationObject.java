package com.example.marta.testobserverv2;

import android.content.Context;
import android.os.Handler;

public class TransactionOperationObject extends TransactionOperation {
    protected TransactionOperationObject(Context context) {
        super(context);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: mObservers) {
            observer.onTransactionDataChanged(mTransaction);
        }
    }

    @Override
    public void get(int ID) {
        mGetTransactionDecorator = new GetTransactionObject();
        mGetTransactionDecorator.setTransaction(ID);
        mGetTransactionDecorator.GetJson();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTransaction = mGetTransactionDecorator.getTrans();
                notifyObservers();
            }
        }, 1200);
    }
}
