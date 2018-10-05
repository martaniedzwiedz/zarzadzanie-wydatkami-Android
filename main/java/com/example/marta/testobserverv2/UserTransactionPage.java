package com.example.marta.testobserverv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
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

public class UserTransactionPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  Observer, AdapterView.OnItemClickListener,View.OnClickListener {

    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    List<HashMap<String, String>> HashList = new ArrayList<>();
    private static final String KEY_ID = "Id";
    private static final String KEY_VALUE = "Value";
    private static final String KEY_DESCRITPION = "Description";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_CREATED = "Created";
    private Subject mUserDataRepository;

    TextView tvHeaderName;
    ImageView imageView;
    TextView SumTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transaction_page);
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

        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.userTransactionListView);
        listView.setOnItemClickListener(this);

        mUserDataRepository = new TransactionOperationList(getApplicationContext());
        mUserDataRepository.registerObserver(this);
        mUserDataRepository.get(1);
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
        getMenuInflater().inflate(R.menu.user_transaction_page, menu);
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
        }
        else if (id == R.id.nav_menu) {
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
    public void onListChange(List<HashMap<String, String>> list) {
        try {
            HashList = list;
            ListAdapter adapter = new SimpleAdapter(UserTransactionPage.this, list, R.layout.transaction_item,
                    new String[]{KEY_ID, KEY_DESCRITPION, KEY_TITLE, KEY_VALUE, KEY_CREATED},
                    new int[]{R.id.id, R.id.title, R.id.description, R.id.value, R.id.date});

            SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.userTransactionListView);
            listView.setAdapter(adapter);
            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            getApplicationContext());
                    deleteItem.setWidth(170);
                    deleteItem.setIcon(R.drawable.delete);
                    menu.addMenuItem(deleteItem);
                }
            };
            listView.setMenuCreator(creator);
            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            openDialog(Integer.valueOf(HashList.get(position).get(KEY_ID)));
                            break;
                    }
                    return false;
                }
            });
        }catch(NullPointerException e) {
            Toast.makeText(UserTransactionPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDialog(final int Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserTransactionPage.this);
        LayoutInflater inflater = UserTransactionPage.this.getLayoutInflater();
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
                        mUserDataRepository = new TransactionOperation(getApplicationContext());
                        mUserDataRepository.registerObserver(UserTransactionPage.this);
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
    public void onTransactionDataChanged(Transaction transaction) {
            startActivity(new Intent(this, UserTransactionPage.class));
    }
    @Override
    public void onSumChange(SumCategory sum) {
        try {
            if (Float.valueOf(sum.getTotal()) < 0)
                SumTransaction.setTextColor(Color.rgb(254, 16, 77));
            else
            SumTransaction.setTextColor(Color.rgb(13, 115, 13));
            SumTransaction.setText(String.format("%.2f", Float.valueOf(sum.getTotal())));
        }catch(NullPointerException e) {
            Toast.makeText(UserTransactionPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageView: startActivity(new Intent(this, EditUserPage.class));break;
        }
    }
    @Override
    public void onCategoryDataChanged(ArrayList<Category> category) {
    }
    @Override
    public void onUserDataChanged(User user) {
    }

}
