package com.example.marta.testobserverv2;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupTransactionPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer, AdapterView.OnItemClickListener, View.OnClickListener {

    private Subject mUserDataRepository;
    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    private ListView mListView;
    List<HashMap<String, String>> HashList = new ArrayList<>();
    private static final String KEY_ID = "Id";
    private static final String KEY_VALUE = "Value";
    private static final String KEY_DESCRITPION = "Description";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_CREATED = "Created";
    TextView tvHeaderName;
    ImageView imageView;
    TextView SumTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_transaction_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserDataRepository = new GroupOperationList(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        SumTransaction = (TextView) findViewById(R.id.SumTransaction);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);
        tvHeaderName= (TextView) navHeaderView.findViewById(R.id.headerGroup);
        imageView = (ImageView) navHeaderView.findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        tvHeaderName.setText(mApplicationPreference.getUserName());
        navigationView.setNavigationItemSelectedListener(this);

        mListView = (ListView) findViewById(R.id.group_list_view);
        mListView.setOnItemClickListener(this);

        mUserDataRepository = new TransactionOperationList(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get(0);
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
        getMenuInflater().inflate(R.menu.group_transaction_page, menu);
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
            startActivity(new Intent(this, MainMenuPage.class));
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
        }else if (id == R.id.nav_menu) {
            startActivity(new Intent(this, MainMenuPage.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,TransactionDetailPage.class);
        Bundle b = new Bundle();
        b.putString("key", HashList.get(position).get(KEY_ID));
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onUserDataChanged(User user) {

    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {
        try {
            HashList = list;
            ListAdapter adapter = new SimpleAdapter(GroupTransactionPage.this, list, R.layout.transaction_item,
                    new String[]{KEY_ID, KEY_DESCRITPION, KEY_TITLE, KEY_VALUE, KEY_CREATED},
                    new int[]{R.id.id, R.id.title, R.id.description, R.id.value, R.id.date});

            mListView.setAdapter(adapter);
        }catch(NullPointerException e){
            Toast.makeText(GroupTransactionPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTransactionDataChanged(Transaction transaction) {
    }

    @Override
    public void onSumChange(SumCategory sum) {
        try {
            if (Float.valueOf(sum.getTotal()) < 0)
                SumTransaction.setTextColor(Color.rgb(254, 16, 77));
            else
                SumTransaction.setTextColor(Color.rgb(13, 115, 13));
            SumTransaction.setText(String.format("%.2f", Float.valueOf(sum.getTotal())));
        }catch(NullPointerException e){
            Toast.makeText(GroupTransactionPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
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
            case R.id.imageView: startActivity(new Intent(this, EditUserPage.class));break;
        }
    }
}
