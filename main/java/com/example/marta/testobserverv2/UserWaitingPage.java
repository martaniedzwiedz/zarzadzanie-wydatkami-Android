package com.example.marta.testobserverv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.marta.testobserverv2.R;

public class UserWaitingPage extends AppCompatActivity implements View.OnClickListener  {

    Button bMenuLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_waiting_page);

        bMenuLogOut = (Button) findViewById(R.id.bMenuLogOut);
        bMenuLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bMenuLogOut: startActivity(new Intent(this, LoginPage.class));
        }
    }
}
