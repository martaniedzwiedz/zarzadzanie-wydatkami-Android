package com.example.marta.testobserverv2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Double.parseDouble;

public class GroupChartTransactionPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer, AdapterView.OnItemClickListener, View.OnClickListener{


    private List<String> value;
    private Subject mUserDataRepository;
    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    TextView tvHeaderName;
    ImageView imageView;
    float total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chart_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mUserDataRepository = new GroupOperationSum(getApplicationContext());
        mUserDataRepository.registerObserver(this);

        mUserDataRepository.get();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);
        tvHeaderName= (TextView) navHeaderView.findViewById(R.id.headerGroup);
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
    }

    @Override
    public void onUserDataChanged(User user) {
    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {
    }

    @Override
    public void onTransactionDataChanged(Transaction transaction) {
    }

    @Override
    public void onSumChange(SumCategory sum) {
        try {
            value = new ArrayList<>();
            value.add(sum.getSum_cat_1());
            value.add(sum.getSum_cat_2());
            value.add(sum.getSum_cat_3());
            value.add(sum.getSum_cat_4());
            value.add(sum.getSum_cat_5());
            value.add(sum.getSum_cat_6());
            value.add(sum.getSum_cat_7());
            value.add(sum.getSum_cat_8());
            value.add(sum.getSum_cat_9());
            value.add(sum.getSum_cat_10());
            total = Float.valueOf(sum.getTotal());
            setupPieChart();
        }catch(NullPointerException e){
            Toast.makeText(GroupChartTransactionPage.this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
        }
    }
    private void setupPieChart() {
        String monthNames[] = {"artykuły spożywcze", "benzyna", "opłaty za samochód", "opłaty mieszkaniowe", "produkty codziennego użytku", "inne", "opłaty stałe", "wpłata", "rozrywka", "odzież"};
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i=0;i<value.size();i++){
            if (!value.get(i).equals("null")) {
                float war = Math.abs(Float.valueOf(value.get(i))/total)*100;
                if(war>0.0)
                    pieEntries.add(new PieEntry(war, monthNames[i]));
            }
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(15);
        dataSet.setValueTextColor(Color.WHITE);


        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
        chart.setDrawSliceText(false);
        chart.setNoDataText("Brak danych do wizualizacji.");
        chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        chart.getLegend().setWordWrapEnabled(false);
        chart.getLegend().setForm(Legend.LegendForm.CIRCLE);
        chart.setUsePercentValues(true);
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
