package com.example.marta.testobserverv2;

import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTransactionListDecorator extends GetJSONDecorator {


    private String Data = "";
    private List<Transaction> d;
    private static final String KEY_ID = "Id";
    private static final String KEY_VALUE = "Value";
    private static final String KEY_DESCRITPION = "Description";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_CREATED = "Created";

    @Override
    public void GetJson() {

        getCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Data = response.body().string();
                        JSONArray res = new JSONArray(Data);
                        Gson gson = new Gson();
                        d = Arrays.asList(gson.fromJson(res.toString(), Transaction[].class));
                        parse();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    int i = 0;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                int i = 0;
            }
        });
    }

    @Override
    public void ParseUser() {
        mAndroidMapList = new ArrayList<>();
        double suma = 0;
        try{
            for (Transaction value : d) {
                if (mApplicationPreference.getUserID() == value.getUser()) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(KEY_ID, toString().valueOf(value.getId()));
                    map.put(KEY_TITLE, toString().valueOf(value.getTitle()));
                    map.put(KEY_DESCRITPION, toString().valueOf(value.getDescription()));
                    map.put(KEY_VALUE, toString().valueOf(value.getValue()));
                    map.put(KEY_CREATED, toString().valueOf(value.getCreated()));
                    mAndroidMapList.add(map);
                    suma += value.getValue();
                }
            }
            sum = new SumCategory();
            sum.setTotal(String.valueOf(suma));
        }catch(NullPointerException e){

        }
    }

    @Override
    public void parseTypeConstant() {
        mAndroidMapList = new ArrayList<>();
        double suma = 0;
        try {
            for (Transaction value : d) {
                if (mApplicationPreference.getUserID() == value.getUser() && value.isConstant() == true) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(KEY_ID, toString().valueOf(value.getId()));
                    map.put(KEY_TITLE, toString().valueOf(value.getTitle()));
                    map.put(KEY_DESCRITPION, toString().valueOf(value.getDescription()));
                    map.put(KEY_VALUE, toString().valueOf(value.getValue()));
                    map.put(KEY_CREATED, toString().valueOf(value.getCreated()));
                    mAndroidMapList.add(map);
                    suma += value.getValue();
                }
            }
            sum = new SumCategory();
            sum.setTotal(String.valueOf(suma));
        }catch(NullPointerException e){
        }
    }

    public void parse(){
        mAndroidMapList = new ArrayList<>();
        float suma = 0;
        try {
        for (Transaction value : d) {
                HashMap<String, String> map = new HashMap<>();
                map.put(KEY_ID, toString().valueOf(value.getId()));
                map.put(KEY_TITLE, toString().valueOf(value.getTitle()));
                map.put(KEY_DESCRITPION, toString().valueOf(value.getDescription()));
                map.put(KEY_VALUE, toString().valueOf(value.getValue()));
                map.put(KEY_CREATED, toString().valueOf(value.getCreated()));
                mAndroidMapList.add(map);
            suma += value.getValue();
        }
        sum = new SumCategory();
        sum.setTotal(String.valueOf(suma));
        }catch(NullPointerException e){
        }
    }

    public GetTransactionListDecorator(GetOperation product) {
        super(product);
    }
}
