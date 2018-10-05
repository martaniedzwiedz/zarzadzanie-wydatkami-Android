package com.example.marta.testobserverv2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSumListDecorator extends GetJSONDecorator{
    private String Data = "";

    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "username";

    @Override
    public void GetJson() {

        getCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        Data = response.body().string();

                        JSONObject res =  new JSONObject(Data);
                        Gson gson = new Gson();
                        sum = gson.fromJson(res.toString(),SumCategory.class);
                    } catch (IOException e){
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {}
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}

        });
    }



    public GetSumListDecorator(GetOperation product) {
        super(product);
    }


}
