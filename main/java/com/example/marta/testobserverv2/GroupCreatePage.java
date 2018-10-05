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

public class GroupCreatePage extends AppCompatActivity implements Observer, View.OnClickListener{

    Button bGroupCreateCancel;
    Button bGroupCreateAdd;
    EditText etgroupName;

    SharedPreference mApplicationPreference =  SharedPreference.getInstance();
    private Subject mUserDataRepository;

    public GroupCreatePage() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create_page);

        etgroupName = (EditText) findViewById(R.id.etgroupName);

        bGroupCreateAdd = (Button) findViewById(R.id.bGroupCreateAdd);
        bGroupCreateAdd.setOnClickListener(this);

        bGroupCreateCancel = (Button) findViewById(R.id.bGroupCreateCancel);
        bGroupCreateCancel.setOnClickListener(this);

        mUserDataRepository = new GroupOperation(getApplicationContext());
        mUserDataRepository.registerObserver(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bGroupCreateAdd:
                if (etgroupName.getText().toString().isEmpty())
                    Toast.makeText(GroupCreatePage.this, "Musisz podać nazwę grupy", Toast.LENGTH_SHORT).show();
                else {
                    mUserDataRepository.post(etgroupName.getText().toString(), toString().valueOf(1));
                }
                break;
            case R.id.bGroupCreateCancel:
                startActivity(new Intent(this, UserSelectGroupPage.class));
                break;
        }
    }

    @Override
    public void onUserDataChanged(User user) {

        startActivity(new Intent(this, MainMenuPage.class));
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
