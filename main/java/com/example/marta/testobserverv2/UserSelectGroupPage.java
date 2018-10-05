package com.example.marta.testobserverv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSelectGroupPage extends AppCompatActivity implements View.OnClickListener {

    Button bGroupJoin;
    Button bGroupCreate;
    Button bMenuLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select_group_page);

        bGroupJoin = (Button) findViewById(R.id.bGroupJoin);
        bGroupJoin.setOnClickListener(this);

        bMenuLogOut = (Button) findViewById(R.id.bMenuLogOut);
        bMenuLogOut.setOnClickListener(this);

        bGroupCreate = (Button) findViewById(R.id.bGroupCreate);
        bGroupCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bGroupJoin:
                startActivity(new Intent(this, GroupJoinPage.class));
                break;
            case R.id.bGroupCreate: startActivity(new Intent(this, GroupCreatePage.class));
                break;
            case R.id.bMenuLogOut: startActivity(new Intent(this, LoginPage.class));break;
            }
    }
}
