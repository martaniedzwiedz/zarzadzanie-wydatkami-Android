package com.example.marta.testobserverv2;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOperation extends ControllerCall implements Subject {
    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    private Context mContext;
    protected ArrayList<Observer> mObservers;
    protected GetOperation mGetUserDecorator;
    private DeleteOperation mDeleteUser;
    protected User mUser;
    protected List<HashMap<String, String>> mAndroidMapList = new ArrayList<>();


    protected UserOperation(Context context) {
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
            observer.onUserDataChanged(mUser);
        }
    }

    @Override
    public void notifyCategoryObserver() {
        for (Observer observer: mObservers) {
        observer.onListChange(mAndroidMapList);
        }
    }


    public void saveUserData(User user) {
        try {
            mApplicationPreference.setUserName(user.getUsername());
            mApplicationPreference.setGroupID(user.getGroup());
            mApplicationPreference.setUserStatus(user.getStatus());
            mApplicationPreference.setUserSentRequest(user.isSendRequest());
            mApplicationPreference.setUserStatus(user.getStatus());
            mApplicationPreference.setUserID(user.getId());
            notifyObservers();
        }catch(NullPointerException e){
            Toast.makeText(mContext, "Błąd poąłczenia", Toast.LENGTH_SHORT).show();
        }

    }

    public void MyData(){
        mGetUserDecorator = new GetUserObject();
        mGetUserDecorator.setMe();
        mGetUserDecorator.GetJson();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUser = mGetUserDecorator.getUser();
                saveUserData(mUser);
            }
        }, 1200);
    }



     public void get(int ID){
     }

    public void post(String username, String password){
        setUser(username, password);
            getUserCall().enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response){
                if(response.isSuccessful()){
                    mApplicationPreference.setToken("Token " + response.body().getToken());
                    MyData();
                  }
                else {
                    Toast.makeText(mContext, "Spróbuj ponownie", Toast.LENGTH_SHORT).show();
                }
            }
              @Override
              public void onFailure(Call<User> call, Throwable t) {
                  Toast.makeText(mContext, "Wystąpił błąd spróbuj ponownie", Toast.LENGTH_SHORT).show();
              }
          });
    }

    public void post(String username, String password, String first_name, String last_name, String email, boolean flag) {
        if(flag)
            setUser(username,password,first_name,last_name,email);
        else
            setEditMe(username,first_name,last_name,email);
            getUserCall().enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response){
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Operacja przebiegła pomyślnie", Toast.LENGTH_SHORT).show();
                    notifyCategoryObserver();
                   }
                else {
                    Toast.makeText(mContext, "Błędne dane", Toast.LENGTH_SHORT).show();
                }
            }
           @Override
            public void onFailure(Call<User> call, Throwable t){
                Toast.makeText(mContext, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void delete(int Id) {
            mDeleteUser = new DeleteOperation();
            mDeleteUser.setDeleteUser(Id);
            mDeleteUser.delete();
            notifyObservers();
    }

    @Override
    public void get() {
    }

    @Override
    public void post(String title, String description, String created, int category, double value, boolean constant) {

    }

}
