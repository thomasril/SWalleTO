package bluejack162.edu.swalleto.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bluejack162.edu.swalleto.R;
import bluejack162.edu.swalleto.fragments.AddFriendFragment;
import bluejack162.edu.swalleto.fragments.CategoryFragment;
import bluejack162.edu.swalleto.fragments.DashboardFragment;
import bluejack162.edu.swalleto.fragments.ExpensesChartFragment;
import bluejack162.edu.swalleto.fragments.IncomeChartFragment;
import bluejack162.edu.swalleto.fragments.PlannedPaymentFragment;
import bluejack162.edu.swalleto.fragments.RecordFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String email, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), MoneyActivity.class);
                startActivity(intent);
                finish();

                // buat munculin alert

                /*AlertDialog.Builder a_builder = new AlertDialog.Builder(HomeActivity.this);

                a_builder.setMessage("What do you want?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = a_builder.create();
                alert.setTitle("Alert mz");
                alert.show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DashboardFragment dashboardFragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, dashboardFragment, "fragment1");
        fragmentTransaction.commit();

        showLoginUser();
    }


    Bitmap bitmap;
    private void showLoginUser () {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        TextView txtUserLoginName = (TextView) headerView.findViewById(R.id.tvUserLoginName);
        TextView txtUserLoginEmail = (TextView) headerView.findViewById(R.id.tvUserLoginEmail);
        ImageView profilePictureUserLogin = (ImageView) headerView.findViewById(R.id.profilePictureUserLogin);

        Intent i = getIntent();
        String userLoginName = i.getStringExtra("name").toString();
        id = i.getStringExtra("id").toString();
        email = i.getStringExtra("email").toString();
        //String url = i.getStringExtra("urlFB").toString();

        txtUserLoginName.setText(userLoginName);
        txtUserLoginEmail.setText(email);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("id",id);
                startActivity(intent);

            }

        });
//        try {
//             bitmap = BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        profilePictureUserLogin.setImageBitmap(bitmap);
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
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            setTitle("Dashboard Fragment");
            DashboardFragment dashboardFragment = new DashboardFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, dashboardFragment, "fragment1");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_record) {
            setTitle("Record Fragment");
            RecordFragment recordFragment = new RecordFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, recordFragment, "fragment2");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_income) {
            setTitle("Income Structure");
            IncomeChartFragment incomeChartFragment = new IncomeChartFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, incomeChartFragment, "fragment3");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_expense) {
            setTitle("Expense Structure");
            ExpensesChartFragment expensesChartFragment = new ExpensesChartFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, expensesChartFragment, "fragment3");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_spendbyCategory) {
            setTitle("Spend By Category");
            CategoryFragment categoryFragment = new CategoryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, categoryFragment, "fragment3");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_plannedPayment) {
            setTitle("Planned Payment");
            PlannedPaymentFragment plannedPaymentFragment = new PlannedPaymentFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, plannedPaymentFragment, "fragment3");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_generate) {
            setTitle("Generate to PDF");
        } else if (id == R.id.nav_add) {
            setTitle("Add Friend");
            AddFriendFragment addFriendFragment = new AddFriendFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, addFriendFragment, "fragment3");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_manageRelationship) {
            setTitle("Manage Relationship");
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "nanti ke logout kok", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
