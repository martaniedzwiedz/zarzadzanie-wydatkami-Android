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

public class AddUserToGroupPage extends AppCompatActivity implements Observer, View.OnClickListener {


    private Subject mUserDataRepository;

    Button bAddUserCancel;
    Button bAddUser;
    EditText etgroupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_to_group_page);

        etgroupName = (EditText) findViewById(R.id.etgroupName);

        bAddUserCancel = (Button) findViewById(R.id.bAddUserCancel);
        bAddUserCancel.setOnClickListener(this);

        bAddUser = (Button) findViewById(R.id.bAddUser);
        bAddUser.setOnClickListener(this);

        mUserDataRepository = new GroupOperation(getApplicationContext());
        mUserDataRepository.registerObserver(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bAddUser:
                if (etgroupName.getText().toString().isEmpty())
                    Toast.makeText(AddUserToGroupPage.this, "Musisz podać nazwę użytkownika", Toast.LENGTH_SHORT).show();
                else
                    mUserDataRepository.post(etgroupName.getText().toString(), toString().valueOf(2));
                break;
            case R.id.bAddUserCancel: startActivity(new Intent(this,UsersPage.class));
                break;
        }
    }

    @Override
    public void onUserDataChanged(User user) {
        startActivity(new Intent(this, UsersPage.class));
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
