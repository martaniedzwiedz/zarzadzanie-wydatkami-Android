package com.example.marta.testobserverv2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static java.lang.Double.parseDouble;

public class AddTransactionPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, Observer {

    EditText Amount;
    EditText DatePicker, transactionTitle, transactionDesc;
    CheckBox checkbox_meat;
    Button bTransactionAdd;
    Button bTransactionCancel;
    private int categoryID;
    private  Subject mUserDataRepository;
    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    TextView tvHeaderName;
    ImageView imageView;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    public void updateLabel(){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        DatePicker.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserDataRepository = new GroupOperation(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);
        tvHeaderName= (TextView) navHeaderView.findViewById(R.id.headerGroup);
        imageView = (ImageView) navHeaderView.findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        tvHeaderName.setText(mApplicationPreference.getUserName());
        navigationView.setNavigationItemSelectedListener(this);

        bTransactionAdd = (Button) findViewById(R.id.bTransactionAdd);
        bTransactionAdd.setOnClickListener(this);
        bTransactionCancel = (Button) findViewById(R.id.bTransactionCancel);
        bTransactionCancel.setOnClickListener(this);

        transactionTitle = (EditText) findViewById(R.id.transactionTitle);
        transactionDesc = (EditText) findViewById(R.id.transactionDesc);
        checkbox_meat = (CheckBox) findViewById(R.id.checkbox_meat);
        DatePicker = (EditText) findViewById(R.id.DatePicker);
        Amount = (EditText) findViewById(R.id.amount);
        Amount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(100,2)});

        mUserDataRepository = new TransactionOperation(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get();

        DatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddTransactionPage.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_transaction_page, menu);
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        Utils2.setBadgeCount(this, icon, mApplicationPreference.getCountRequest());
        if(!mApplicationPreference.getUserStatus().equals("administrator")){
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bBack) {
            startActivity(new Intent(this,MainMenuPage.class));
        }else if (id == R.id.bLogOut){
            startActivity(new Intent(this,LoginPage.class));
        }else if(id == R.id.action_notifications){
            startActivity(new Intent(this, JoinRequestPage.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_users) {
            startActivity(new Intent(this, UsersPage.class));
        } else if (id == R.id.nav_read_csv) {
            startActivity(new Intent(this,ReadCsvTransactionPage.class));
        } else if (id == R.id.nav_add_transaction) {
            startActivity(new Intent(this,AddTransactionPage.class));
        } else if (id == R.id.nav_group_transaction) {
            startActivity(new Intent(this, GroupTransactionPage.class));
        } else if (id == R.id.nav_user_transaction) {
            startActivity(new Intent(this, UserTransactionPage.class));
        } else if (id == R.id.nav_period_uptil_now) {
            startActivity(new Intent(this, ConstantTransactionPage.class));
        }else if (id == R.id.nav_charts) {
            startActivity(new Intent(this, GroupChartTransactionPage.class));
        }else if (id == R.id.nav_menu) {
            startActivity(new Intent(this, MainMenuPage.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bTransactionAdd:
               if(categoryID==8)
                   mUserDataRepository.post(transactionDesc.getText().toString(), transactionTitle.getText().toString(),DatePicker.getText().toString(), Integer.valueOf(categoryID), parseDouble(Amount.getText().toString()), checkbox_meat.isChecked());
                else
                   mUserDataRepository.post(transactionDesc.getText().toString(), transactionTitle.getText().toString(),DatePicker.getText().toString(), Integer.valueOf(categoryID), -parseDouble(Amount.getText().toString()), checkbox_meat.isChecked());
                break;
            case R.id.bTransactionCancel: startActivity(new Intent(this, MainMenuPage.class));break;
            case R.id.imageView: startActivity(new Intent(this, EditUserPage.class));break;
        }
    }

    @Override
    public void onUserDataChanged(User user) {

    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {

    }

    @Override
    public void onTransactionDataChanged(Transaction transaction) {
                startActivity(new Intent(this, AddTransactionPage.class));
    }

    @Override
    public void onSumChange(SumCategory sum) {

    }

    @Override
    public void onCategoryDataChanged(ArrayList<Category> category) {
        try {
            final ArrayList<Category> CategoryCategoryList = new ArrayList<Category>();
            ArrayList<String> CategoryStringList = new ArrayList<String>();
            for (Category value : category) {
                Category cat = new Category.BuilderCategory()
                        .id(value.getId())
                        .Name(value.getName())
                        .build();
                CategoryCategoryList.add(cat);
                CategoryStringList.add(value.getName());
            }
            Spinner mySpinner = (Spinner) findViewById(R.id.planets_spinner);

            mySpinner
                    .setAdapter(new ArrayAdapter<String>(AddTransactionPage.this,
                            android.R.layout.simple_spinner_dropdown_item, CategoryStringList));
            mySpinner
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            categoryID = CategoryCategoryList.get(position).getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
        }catch (NullPointerException e){
            Toast.makeText(AddTransactionPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
    }
}
