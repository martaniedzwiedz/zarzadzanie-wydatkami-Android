package com.example.marta.testobserverv2;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadCsvTransactionPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer ,View.OnClickListener{

    public Subject mUserDataRepository;
    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    private static String PATH_TO_SERVER;// = "http://tyrebox.pl/csv/data.csv";
    private List<Transaction> list=new ArrayList<>();
    TextView tvHeaderName;
    ImageView imageView;
    Button bRead;
    Button bCancel;
    EditText etDirectory;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_csv_transaction_page);
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

        bRead = (Button) findViewById(R.id.bRead);
        bRead.setOnClickListener(this);
        bCancel = (Button) findViewById(R.id.bCancel);
        bCancel.setOnClickListener(this);
        etDirectory = (EditText) findViewById(R.id.etDirectory);

        mUserDataRepository =  new TransactionOperation(getApplicationContext());
        mUserDataRepository.registerObserver(this);

        toast = Toast.makeText(ReadCsvTransactionPage.this, "Niepoprawna ściażka dostępu do pliku", Toast.LENGTH_LONG);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bRead:
                if(!etDirectory.getText().toString().isEmpty()) {
                    PATH_TO_SERVER = etDirectory.getText().toString();
                    DownloadFilesTask downloadFilesTask = new DownloadFilesTask();
                    downloadFilesTask.execute();
                }
                else
                    Toast.makeText(ReadCsvTransactionPage.this, "Musisz podać ścieżkę do pliku", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bCancel:
            startActivity(new Intent(this, MainMenuPage.class));break;
            case R.id.imageView: startActivity(new Intent(this, EditUserPage.class));break;
        }
    }


    private class DownloadFilesTask extends AsyncTask<URL, Void, List<Transaction>> {

        protected List<Transaction> doInBackground(URL... urls){
            return downloadRemoteTextFileContent();
        }
    }

    private List<Transaction> downloadRemoteTextFileContent(){
        URL mUrl = null;
        String[] content = null;
        try{
            mUrl = new URL(PATH_TO_SERVER);
            try {
                assert mUrl != null;
                URLConnection connection = mUrl.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(";");
                    Transaction transaction = new Transaction.BuilderTransaction()
                            .Description(tokens[1])
                            .Title(tokens[0])
                            .Created(tokens[3])
                            .Value(Double.valueOf(tokens[2]))
                            .Constant(false)
                            .Category(7)
                            .build();
                    list.add(transaction);
                    mUserDataRepository.post(transaction.getDescription(),transaction.getTitle(), transaction.getCreated(), transaction.getCategory(),-transaction.getValue(),transaction.isConstant() );
                }
                br.close();
            }catch (IOException e){

                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            toast.show();
           // Toast.makeText(this, "Wystąpił problem z połączeniem.", Toast.LENGTH_SHORT).show();
          //  e.printStackTrace();
        }
        return list;
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
    public void onUserDataChanged(User user) {

    }

    @Override
    public void onListChange(List<HashMap<String, String>> list) {

    }

    @Override
    public void onTransactionDataChanged(Transaction transaction) {
        startActivity(new Intent(this, UserTransactionPage.class));
    }

    @Override
    public void onSumChange(SumCategory sum) {

    }

    @Override
    public void onCategoryDataChanged(ArrayList<Category> category) {

    }
}
