package com.example.marta.testobserverv2;

import android.content.Context;
import android.os.Handler;

public class TransactionOperationList extends TransactionOperation {

    protected TransactionOperationList(Context context) {
        super(context);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: mObservers) {
            observer.onListChange(mAndroidMapList);
            observer.onSumChange(sumCategory);
        }
    }

    @Override
    public void get(final int i) {
        mGetTransactionDecorator = new GetTransactionListDecorator( mGetTransactionDecorator);
        mGetTransactionDecorator.setTransactionsList();
        mGetTransactionDecorator.GetJson();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (i){
                    case 0: break;
                    case 1: mGetTransactionDecorator.ParseUser();break;
                    case 2: mGetTransactionDecorator.parseTypeConstant();break;
                }
                mAndroidMapList =  mGetTransactionDecorator.getList();
                sumCategory = mGetTransactionDecorator.getSum();
                notifyObservers();
            }
        }, 1200);
    }

}
