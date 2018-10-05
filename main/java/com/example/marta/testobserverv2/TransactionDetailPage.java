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

public class TransactionDetailPage extends AppCompatActivity implements Observer, View.OnClickListener {

    TextView dtitle;
    TextView ddescription;
    TextView dcategory;
    TextView ddata;
    TextView dkwota;
    TextView duser;
    TextView dwydatekstaly;
    Button bTransactionClose;
    private int mTransId;
    private  Subject mUserDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        bTransactionClose = (Button) findViewById(R.id.bTransactionClose);
        bTransactionClose.setOnClickListener(this);


        dtitle = (TextView) findViewById(R.id.dtitle);
        dcategory = (TextView)findViewById(R.id.dcategory);
        ddata = (TextView)findViewById(R.id.ddata);
        ddescription = (TextView)findViewById(R.id.ddescription);
        duser = (TextView)findViewById(R.id.duser);
        dkwota = (TextView)findViewById(R.id.dkwota);
        dwydatekstaly = (TextView)findViewById(R.id.dwydatekstaly);

        Bundle b = getIntent().getExtras();
        mTransId = Integer.valueOf(b.getString("key"));
        mUserDataRepository = new TransactionOperationObject(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get(mTransId);
    }

   @Override
    public void onUserDataChanged(User user) {

    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {

    }

    @Override
    public void onTransactionDataChanged(Transaction transaction) {
        try {
            dtitle.setText(transaction.getTitle());
            ddescription.setText(transaction.getDescription());
            ddata.setText(transaction.getCreated());
            dkwota.setText(toString().valueOf(transaction.getValue()));
            dcategory.setText(transaction.getCategoryName());
            duser.setText(transaction.getUserName());
            if (transaction.isConstant())
                dwydatekstaly.setText("Tak");
            else
                dwydatekstaly.setText("Nie");
        }catch(NullPointerException e){
            Toast.makeText(TransactionDetailPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSumChange(SumCategory sum) {

    }

    @Override
    public void onCategoryDataChanged(ArrayList<Category> category) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserDataRepository.removeObserver(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bTransactionClose:
                finish();
                break;
        }
    }
}
