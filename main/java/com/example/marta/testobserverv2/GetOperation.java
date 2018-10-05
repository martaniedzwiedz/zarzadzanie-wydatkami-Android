package com.example.marta.testobserverv2;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class GetOperation extends ControllerCall{

    public User getUser() {
        return user;
    }
    public Transaction getTrans() {return trans;}
    public SumCategory getSum() {return sum;}
    protected SharedPreference mApplicationPreference = SharedPreference.getInstance();
    protected User user;
    protected SumCategory sum;
    protected Transaction trans;
    public ArrayList<Category> getCategoryList() {return CategoryList;}
    protected ArrayList<Category> CategoryList;
    protected Category category;
    public Category getCategory() {return category;}
    public List<HashMap<String, String>> getList() {
        return mAndroidMapList;
    }
    protected List<HashMap<String, String>> mAndroidMapList;
    public abstract void GetJson();
    public void ParseUser() {};
    public void ParseCategory(int id) {}
    public void parseTypeConstant(){};

}
