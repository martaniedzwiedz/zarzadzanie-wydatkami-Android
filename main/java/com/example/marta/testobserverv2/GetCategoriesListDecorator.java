package com.example.marta.testobserverv2;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCategoriesListDecorator extends GetJSONDecorator {


    private String Data = "";
    @Override
    public void GetJson() {
        getCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        Data = response.body().string();

                        JSONArray res =  new JSONArray(Data);
                        Gson gson = new Gson();
                        Type founderListType = new TypeToken<ArrayList<Category>>(){}.getType();
                        CategoryList = gson.fromJson(res.toString(),founderListType);
                    } catch (IOException e){
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) { }

        });


    }

    @Override
    public void ParseCategory(int id){
      for (Category value : CategoryList) {
            if(id == value.getId()) {
                    category = value;
                    break;
            }
        }
    }

    public GetCategoriesListDecorator(GetOperation product) {
        super(product);
    }

}
