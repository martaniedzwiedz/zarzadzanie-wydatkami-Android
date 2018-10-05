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

public class GroupOperation extends ControllerCall implements Subject {
    protected ArrayList<Observer> mObservers;
    protected SharedPreference mApplicationPreference = SharedPreference.getInstance();
    protected GetOperation mGetUserDecorator;
    protected Context mContext;
    private DeleteOperation mRequest;
    protected User user;
    protected UserOperation userOperation;
    protected SumCategory sum;
    protected int size;
    protected List<HashMap<String, String>> mAndroidMapList = new ArrayList<>();

    protected GroupOperation(Context context) {
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
            userOperation = new UserOperation(mContext);
            userOperation.MyData();
            observer.onUserDataChanged(user);
        }
    }

    @Override
    public void notifyCategoryObserver() {
    }


    @Override
    public void get() {
    }

    @Override
    public void delete(int Id) {
        mRequest = new DeleteOperation();
        mRequest.SetDeclineRequest(Id);
        mRequest.delete();
        notifyObservers();
    }
    @Override
    public void get(int ID) {
        mRequest = new DeleteOperation();
        mRequest.SetAcceptRequest(ID);
        mRequest.delete();
        notifyObservers();
    }

    @Override
    public void post(String group_name, String password) {
         switch(Integer.valueOf(password)){
            case 0: SetGroupJoin(group_name);break;
            case 1: SetCreateGroup(group_name);break;
            case 2: SetAddToGroup(group_name);break;
        }
        getGroupCall().enqueue(new Callback<Group>(){
            @Override
            public void onResponse(Call<Group> call, Response<Group> response){

                if(response.isSuccessful()) {
                    Toast.makeText(mContext, "Operacja zakończona powodzeniem", Toast.LENGTH_SHORT).show();
                    notifyObservers();
                }
                else {
                    Toast.makeText(mContext, "Nieproawidłowe dane", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Group> call, Throwable t){
                Toast.makeText(mContext, "Wystąpił błąd spróbuj ponownie", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void post(String title, String description, String created, int category, double value, boolean constant) {

    }

    @Override
    public void post(String username, String password, String first_name, String last_name, String email, boolean flag) {

    }
}
