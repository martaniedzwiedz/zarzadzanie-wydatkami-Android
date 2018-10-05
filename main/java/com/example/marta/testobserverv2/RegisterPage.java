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

public class RegisterPage extends AppCompatActivity implements Observer,  View.OnClickListener {

    EditText regusername,regfirstname,reglastname,regpassword, regemail;
    Button bregister, bBack;
    public Subject mUserDataRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mUserDataRepository =  new UserOperation(getApplicationContext());
        mUserDataRepository.registerObserver(this);

        regusername = (EditText) findViewById(R.id.regUsername);
        regfirstname = (EditText) findViewById(R.id.regFirstName);
        reglastname = (EditText) findViewById(R.id.regLastName);
        regpassword = (EditText) findViewById(R.id.regPass);
        bregister = (Button) findViewById(R.id.bregister);
        regemail = (EditText) findViewById(R.id.regemail);
        bBack = (Button) findViewById(R.id.bBack);
        bregister.setOnClickListener(this);
        bBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bregister:
                if(!regusername.getText().toString().isEmpty() && !regpassword.getText().toString().isEmpty())
                    mUserDataRepository.post(regusername.getText().toString(), regpassword.getText().toString(), regfirstname.getText().toString(), reglastname.getText().toString(), regemail.getText().toString(), true);
                else if(regusername.getText().toString().isEmpty())
                    Toast.makeText(RegisterPage.this, "Pole: 'Nazwa użytkownika' nie może być puste", Toast.LENGTH_SHORT).show();
                else if(regpassword.getText().toString().isEmpty())
                    Toast.makeText(RegisterPage.this, "Pole: 'Hasło' nie może być puste", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bBack: startActivity(new Intent(this,LoginPage.class));break;
        }
    }

    @Override
    public void onUserDataChanged(User user) {
    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {
        startActivity(new Intent(this, LoginPage.class));
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
