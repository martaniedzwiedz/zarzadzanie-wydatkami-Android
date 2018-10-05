package com.example.marta.testobserverv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDetailPage extends AppCompatActivity implements Observer, View.OnClickListener{

    private  Subject mUserDataRepository;
    TextView dusername;
    TextView dname;
    TextView dlastname;
    TextView demail;
    Button bCancel;
    private int UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_page);

        Bundle b = getIntent().getExtras();
        UserId = Integer.valueOf(b.getString("key"));

        dusername = (TextView) findViewById(R.id.dusername);
        dname = (TextView)findViewById(R.id.dname);
        dlastname = (TextView)findViewById(R.id.dlastname);
        demail = (TextView)findViewById(R.id.demail);
        bCancel = (Button) findViewById(R.id.bClose);
        bCancel.setOnClickListener(this);

        mUserDataRepository = new UserOperationObject(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get(UserId);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bClose:
                finish();
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
            dusername.setText(user.getUsername());
            dname.setText(user.getFirstName());
            dlastname.setText(user.getLastName());
            demail.setText(user.getEmail());
        }catch(NullPointerException e){
            Toast.makeText(UserDetailPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
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
