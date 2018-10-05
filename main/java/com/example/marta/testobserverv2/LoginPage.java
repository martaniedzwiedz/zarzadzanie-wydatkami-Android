package com.example.marta.testobserverv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginPage extends AppCompatActivity implements Observer,  View.OnClickListener {
    private Subject mUserDataRepository;
    private SharedPreference ApplicationPreference = SharedPreference.getInstance();
    Button blogin;
    EditText etpassword, etusername;
    TextView bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mUserDataRepository = new UserOperation(getApplicationContext());
        mUserDataRepository.registerObserver(this);

        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);
        bRegister = (TextView)findViewById(R.id.bRegister);
        blogin = (Button) findViewById(R.id.blogin);

        blogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserDataRepository.removeObserver(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.blogin:
                if(!etusername.getText().toString().isEmpty() && !etpassword.getText().toString().isEmpty())
                   mUserDataRepository.post(etusername.getText().toString(),etpassword.getText().toString() );
                else if(etusername.getText().toString().isEmpty())
                    Toast.makeText(LoginPage.this, "Pole: 'Nazwa użytkownika' nie może być puste", Toast.LENGTH_SHORT).show();
                else if(etpassword.getText().toString().isEmpty())
                    Toast.makeText(LoginPage.this, "Pole: 'Hasło' nie może być puste", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bRegister:
                startActivity(new Intent(this, RegisterPage.class));
                break;
        }
    }

    @Override
    public void onUserDataChanged(User user) {
        if(ApplicationPreference.getGroupID()!= 0)
            startActivity(new Intent(this, MainMenuPage.class));
        else if(ApplicationPreference.getGroupID() == 0 && ApplicationPreference.isUserSentRequest()==false)
            startActivity(new Intent(this, UserSelectGroupPage.class));
        else if(ApplicationPreference.getGroupID() == 0 && ApplicationPreference.isUserSentRequest()==true)
            startActivity(new Intent(this, UserWaitingPage.class));
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

    @Override
    public void onListChange(List<HashMap<String, String>> list) {

    }
}
