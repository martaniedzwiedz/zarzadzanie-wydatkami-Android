package com.example.marta.testobserverv2;

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

public class GetRequestListDecorator extends GetJSONDecorator{
    private String Data = "";
    private List<Group> d;

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

                        JSONArray res =  new JSONArray(Data);

                        Gson gson = new Gson();
                        Type founderListType = new TypeToken<ArrayList<Group>>(){}.getType();
                        d = gson.fromJson(res.toString(),founderListType);
                        parseRequest();
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



    public GetRequestListDecorator(GetOperation product) {
        super(product);
    }

    public void parseRequest(){
        mAndroidMapList = new ArrayList<>();
        for (Group value : d) {
            HashMap<String,String> map = new HashMap<>();
            map.put(KEY_ID,toString().valueOf(value.getId()) );
            map.put(KEY_NAME,toString().valueOf(value.getUsername()) );
            mAndroidMapList.add(map);
        }
    }

}
