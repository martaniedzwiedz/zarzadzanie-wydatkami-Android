package com.example.marta.testobserverv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import static android.graphics.Color.WHITE;
import static java.lang.Integer.valueOf;

public class UsersPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer, AdapterView.OnItemClickListener, View.OnClickListener {

    private Subject mUserDataRepository;
    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    List<HashMap<String, String>> HashList = new ArrayList<>();

    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "username";
    TextView tvHeaderName;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_page);

        mUserDataRepository = new GroupOperationList(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserDataRepository = new UserOperationList(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get(0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsersPage.this, AddUserToGroupPage.class));
            }
        });
        if(!mApplicationPreference.getUserStatus().equals("administrator")){
            fab.setVisibility(View.GONE);
        }

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

        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.usersListView);
        listView.setOnItemClickListener(this);
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
        getMenuInflater().inflate(R.menu.users_page, menu);
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
    public void onUserDataChanged(User user) {
        startActivity(new Intent(this, UsersPage.class));
    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {
        try {
            HashList = list;
            ListAdapter adapter = new SimpleAdapter(UsersPage.this, list, R.layout.list_item,
                    new String[]{KEY_ID, KEY_NAME},
                    new int[]{R.id.version, R.id.name});
            SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.usersListView);
            listView.setAdapter(adapter);
            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            getApplicationContext());
                    deleteItem.setWidth(170);
                    deleteItem.setIcon(R.drawable.delete);
                    if (mApplicationPreference.getUserStatus().equals("administrator")) {
                        menu.addMenuItem(deleteItem);
                    }
                }
            };
            listView.setMenuCreator(creator);
            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            //delete
                            openDialog(valueOf(HashList.get(position).get(KEY_ID)));
                            break;
                    }
                    return false;
                }
            });
        }catch(NullPointerException e){
            Toast.makeText(UsersPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDialog(final int Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UsersPage.this);
        LayoutInflater inflater = UsersPage.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogdeleteuser, null);
         builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUserDataRepository = new UserOperation(getApplicationContext());
                        mUserDataRepository.registerObserver(UsersPage.this);
                        mUserDataRepository.delete(Id);
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimaryDark);
        Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 8;
        layoutParams.setMargins(30,0,30,0);
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
        btnPositive.setBackgroundResource(R.drawable.buttonstyle);
        btnPositive.setTextColor(WHITE);
        btnNegative.setBackgroundResource(R.drawable.buttonoutlinestyle);
        btnNegative.setTextColor(WHITE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Intent intent = new Intent(this,UserDetailPage.class);
        Bundle b = new Bundle();
        b.putString("key", HashList.get(i).get(KEY_ID));
        intent.putExtras(b);
        startActivity(intent);
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageView: startActivity(new Intent(this, EditUserPage.class));break;
        }
    }
}
