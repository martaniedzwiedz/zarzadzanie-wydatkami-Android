package com.example.marta.testobserverv2;

import android.content.Intent;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JoinRequestPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  Observer, AdapterView.OnItemClickListener, View.OnClickListener {

    private Subject mUserDataRepository;
    private List<HashMap<String, String>> mAndroidMapList = new ArrayList<>();
    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "username";
    TextView tvHeaderName;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.RequestUsersListView);
        listView.setOnItemClickListener(this);

        mUserDataRepository = new GroupOperationList(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get();
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
        getMenuInflater().inflate(R.menu.join_request_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bBack) {
            startActivity(new Intent(this, UsersPage.class));
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

    }

    @Override
    public void onUserDataChanged(User user) {
                startActivity(new Intent(this, JoinRequestPage.class));
    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {
        try {
            mAndroidMapList = list;
            ListAdapter adapter = new SimpleAdapter(JoinRequestPage.this, mAndroidMapList, R.layout.list_item,
                    new String[]{KEY_ID, KEY_NAME},
                    new int[]{R.id.version, R.id.name});

            SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.RequestUsersListView);
            listView.setAdapter(adapter);
            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    SwipeMenuItem acceptItem = new SwipeMenuItem(
                            getApplicationContext());
                    acceptItem.setWidth(140);
                    acceptItem.setIcon(R.drawable.ic_done_all_black_24dp);
                    menu.addMenuItem(acceptItem);
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            getApplicationContext());
                    deleteItem.setWidth(140);
                    deleteItem.setIcon(R.drawable.delete);
                    menu.addMenuItem(deleteItem);
                }
            };
            listView.setMenuCreator(creator);
            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    mUserDataRepository = new GroupOperation(getApplicationContext());
                    mUserDataRepository.registerObserver(JoinRequestPage.this);
                    switch (index) {
                        case 0:
                            mUserDataRepository.get(Integer.valueOf(mAndroidMapList.get(position).get(KEY_ID)));
                            startActivity(new Intent(getApplicationContext(), JoinRequestPage.class));
                            ;
                            break;
                        case 1:
                            mUserDataRepository.delete(Integer.valueOf(mAndroidMapList.get(position).get(KEY_ID)));
                            startActivity(new Intent(getApplicationContext(), JoinRequestPage.class));
                            break;
                    }
                    return false;
                }
            });
        }catch(NullPointerException e){
            Toast.makeText(JoinRequestPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageView: startActivity(new Intent(this, EditUserPage.class));break;
        }
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
