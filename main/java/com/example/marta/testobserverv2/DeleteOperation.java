package com.example.marta.testobserverv2;

import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteOperation extends ControllerCall{

    public void delete(){

        getCall().enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){

                if(response.isSuccessful()){ }
                else { }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t){
            }
        });
    }
}
