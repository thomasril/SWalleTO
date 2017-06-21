package bluejack162.edu.swalleto.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bluejack162.edu.swalleto.R;
import bluejack162.edu.swalleto.TabAdapter;
import bluejack162.edu.swalleto.fragments.AddExpenseFragment;
import bluejack162.edu.swalleto.fragments.AddIncomeFragment;

public class MoneyActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.add(new AddIncomeFragment(), "Income");
        tabAdapter.add(new AddExpenseFragment(), "Expense");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
