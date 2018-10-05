package com.example.marta.testobserverv2;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionOperation extends  ControllerCall implements Subject{
    protected Transaction mTransaction;

    private ArrayList<Category> mListCategory;
    protected ArrayList<Observer> mObservers;
    private Context mContext;
    protected GetOperation mGetTransactionDecorator;
    private DeleteOperation mDeleteTransaction;
    protected SumCategory sumCategory;
    protected List<HashMap<String, String>> mAndroidMapList = new ArrayList<>();

    protected TransactionOperation (Context context) {
        mObservers = new ArrayList<>();
        mContext=context;
    }

    @Override
    public void registerObserver(Observer repositoryObserver) {
        if(!mObservers.contains(repositoryObserver)) {
            mObservers.add(repositoryObserver);
        }
    }
    @Override
    public void removeObserver(Observer repositoryObserver) {
        if(mObservers.contains(repositoryObserver)) {
            mObservers.remove(repositoryObserver);
        }
    }
    @Override
    public void notifyObservers() {
        for (Observer observer: mObservers) {
            observer.onTransactionDataChanged(mTransaction);
        }
    }


    @Override
    public void notifyCategoryObserver() {
        for (Observer observer: mObservers) {
            observer.onCategoryDataChanged(mListCategory);
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

    public void get(){
        mGetTransactionDecorator = new GetCategoriesListDecorator(mGetTransactionDecorator);
        mGetTransactionDecorator.setCategoriesList();
        mGetTransactionDecorator.GetJson();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListCategory = mGetTransactionDecorator.getCategoryList();
                notifyCategoryObserver();
            }
        }, 1200);
    }

    @Override
    public void post(String title, String description, String created, int category, double value, boolean constant) {
        setTransaction( title, description, created, category,  value,constant);
        getTransactionCall().enqueue(new Callback<Transaction>(){
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response){

                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Transakcja została poprawnie dodana", Toast.LENGTH_SHORT).show();
                    notifyObservers();
                }
                else {
                    Toast.makeText(mContext, "Wystąpił bład", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Transaction> call, Throwable t){
                Toast.makeText(mContext, "Wystąpił bład połączenia", Toast.LENGTH_SHORT).show();
            }
        });
     }

    @Override
    public void delete(int Id) {
        mDeleteTransaction = new DeleteOperation();
        mDeleteTransaction.setDeleteTransaction(Id);
        mDeleteTransaction.delete();
        notifyObservers();
    }
    @Override
    public void post(String username, String password) {
        if (username.equals("false")) {
            setEditTrans(false, Integer.valueOf(password));
            getCall().enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        notifyObservers();
                    } else {
                        Toast.makeText(mContext, "Wystąpił bład", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(mContext, "Wystąpił bład połączenia", Toast.LENGTH_SHORT).show();
                }
            });
        }else if(username.equals("true")){
            get(Integer.valueOf(password));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        post(mTransaction.getTitle(), mTransaction.getDescription(), mTransaction.getCreated(), mTransaction.getCategory(), mTransaction.getValue(), mTransaction.isConstant());
                    }catch(NullPointerException e){
                        Toast.makeText(mContext, "Wystąpił bład połączenia", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 1200);
        }
    }
    @Override
    public void post(String username, String password, String first_name, String last_name, String email, boolean flag) {
    }
}
