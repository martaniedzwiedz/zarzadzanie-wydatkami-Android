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

public class EditUserPage extends AppCompatActivity implements Observer, View.OnClickListener{

    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    private  Subject mUserDataRepository;
    Button bEdit;
    Button bEditCancel;

    EditText bEditUsername;
    EditText bEditName;
    EditText bEditLastName;
    EditText bEditEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_page);

        bEdit = (Button)findViewById(R.id.bEdit);
        bEditCancel = (Button) findViewById(R.id.bEditCancel);

        bEdit.setOnClickListener(this);
        bEditCancel.setOnClickListener(this);

        bEditUsername = (EditText)findViewById(R.id.beditUsername);
        bEditName = (EditText)findViewById(R.id.beditName);
        bEditLastName = (EditText)findViewById(R.id.beditLastName);
        bEditEmail = (EditText)findViewById(R.id.beditemail);

        mUserDataRepository = new UserOperationObject(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get(mApplicationPreference.getUserID());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bEdit:
                mUserDataRepository = new UserOperation(getApplicationContext());
                mUserDataRepository.registerObserver(EditUserPage.this);
                mUserDataRepository.post(bEditUsername.getText().toString(),null,bEditName.getText().toString(),bEditLastName.getText().toString(),bEditEmail.getText().toString(), false);
                break;
            case R.id.bEditCancel:
                startActivity(new Intent(this, MainMenuPage.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserDataRepository.removeObserver(this);
    }


    @Override
    public void onUserDataChanged(User user) {
        try {
            bEditUsername.setText(user.getUsername());
            bEditName.setText(user.getFirstName());
            bEditLastName.setText(user.getLastName());
            bEditEmail.setText(user.getEmail());
        }catch(NullPointerException e){
            Toast.makeText(EditUserPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {
        startActivity(new Intent(this, EditUserPage.class));
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
