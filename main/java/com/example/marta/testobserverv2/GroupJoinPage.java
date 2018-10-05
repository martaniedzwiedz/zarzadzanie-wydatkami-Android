package com.example.marta.testobserverv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupJoinPage extends AppCompatActivity implements Observer, View.OnClickListener{

    Button bGroupJoinCancel;
    Button bGroupJoinAdd;
    EditText etgroupNameJoin;

    private Subject mUserDataRepository;

    public GroupJoinPage() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_join_page);

        etgroupNameJoin = (EditText) findViewById(R.id.etgroupNameJoin);
        bGroupJoinAdd = (Button) findViewById(R.id.bGroupJoinAdd);
        bGroupJoinAdd.setOnClickListener(this);
        bGroupJoinCancel = (Button) findViewById(R.id.bGroupJoinCancel);
        bGroupJoinCancel.setOnClickListener(this);

        mUserDataRepository = new GroupOperation(getApplicationContext());
        mUserDataRepository.registerObserver(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bGroupJoinAdd:
                if (etgroupNameJoin.getText().toString().isEmpty())
                    Toast.makeText(GroupJoinPage.this, "Musisz podać nazwę grupy", Toast.LENGTH_SHORT).show();
                else
                    mUserDataRepository.post(etgroupNameJoin.getText().toString(), toString().valueOf(0));
                break;
            case R.id.bGroupJoinCancel: startActivity(new Intent(this, UserSelectGroupPage.class));
                break;
        }
    }

    @Override
    public void onUserDataChanged(User user) {
            startActivity(new Intent(this, UserWaitingPage.class));
    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {

    }

    @Override
    public void onTransactionDataChanged(Transaction transaction) {

    }

    @Override
    public void onSumChange(SumCategory sum) {

    }

    @Override
    public void onCategoryDataChanged(ArrayList<Category> category) {

    }
}
