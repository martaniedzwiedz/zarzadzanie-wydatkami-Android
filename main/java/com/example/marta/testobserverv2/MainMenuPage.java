package com.example.marta.testobserverv2;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainMenuPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer, View.OnClickListener {


    Button bMenuLogOut;
    Menu menu;
    Button bUsers;
    Button bTransactions;
    Button bGroupTransaction;
    Button bUserTransaction;
    Button bConstantTransaction;
    Button bReadCsv;
    Button bChart;
    TextView tvHeaderName;
    ImageView imageView;
    private Subject mUserDataRepository;
    private SharedPreference mApplicationPreference = SharedPreference.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mUserDataRepository = new GroupOperationList(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get();

        bUsers = (Button) findViewById(R.id.bUsers);
        bUsers.setOnClickListener(this);
        bTransactions = (Button) findViewById(R.id.bTransactions);
        bTransactions.setOnClickListener(this);
        bGroupTransaction = (Button) findViewById(R.id.bGroupTransaction);
        bGroupTransaction.setOnClickListener(this);
        bUserTransaction = (Button) findViewById(R.id.bUserTransaction);
        bUserTransaction.setOnClickListener(this);
        bConstantTransaction = (Button) findViewById(R.id.bConstantTransaction);
        bConstantTransaction.setOnClickListener(this);
        bReadCsv = (Button) findViewById(R.id.bReadCsv);
        bReadCsv.setOnClickListener(this);
        bChart = (Button) findViewById(R.id.bChart);
        bChart.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);
        tvHeaderName = (TextView) navHeaderView.findViewById(R.id.headerGroup);
        imageView = (ImageView) navHeaderView.findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        tvHeaderName.setText(mApplicationPreference.getUserName());
        navigationView.setNavigationItemSelectedListener(this);
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
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu_page, menu);
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
        if (id == R.id.bEdit) {
            startActivity(new Intent(this,EditUserPage.class));
        }else if (id == R.id.bLogOut){
            startActivity(new Intent(this, LoginPage.class));
        }else if(id == R.id.action_notifications){
            startActivity(new Intent(this, JoinRequestPage.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
        }
        else if (id == R.id.nav_menu) {
            startActivity(new Intent(this, MainMenuPage.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageView: startActivity(new Intent(this, EditUserPage.class));break;
            case R.id.bUsers: startActivity(new Intent(this, UsersPage.class)); break;
            case R.id.bTransactions: startActivity(new Intent(this, AddTransactionPage.class)); break;
            case R.id.bGroupTransaction: startActivity(new Intent(this,GroupTransactionPage.class));break;
            case R.id.bUserTransaction: startActivity(new Intent(this,UserTransactionPage.class));break;
            case R.id.bConstantTransaction: startActivity(new Intent(this,ConstantTransactionPage.class));break;
            case R.id.bReadCsv: startActivity(new Intent(this, ReadCsvTransactionPage.class));break;
            case R.id.bChart: startActivity(new Intent(this, GroupChartTransactionPage.class));break;
        }
    }
    @Override
    public void onListChange(List<HashMap<String, String>> list) {
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        Utils2.setBadgeCount(this, icon, mApplicationPreference.getCountRequest());
    }

    @Override
    public void onUserDataChanged(User user) {
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
