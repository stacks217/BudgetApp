package edu.uci.stacks.easybudget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.activity.fragment.adapter.MonthViewPagerAdapter;
import edu.uci.stacks.easybudget.data.BudgetConfig;
import edu.uci.stacks.easybudget.data.BudgetMode;
import edu.uci.stacks.easybudget.data.category.CategoryData;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransactionData;
import edu.uci.stacks.easybudget.service.NotificationReminderService;

public class MainActivity extends BudgetActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    BudgetConfig budgetConfig;

    @Inject
    CategoryData data;

    @Inject
    MoneyTransactionData moneyTransactionData;
    private ViewPager mPager;
    private MonthViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // start this service if user ever comes to the app so notifications work based on alarms
        startService(new Intent(this, NotificationReminderService.class));

        setContentView(R.layout.activity_main);
        BudgetApplication.getComponent().inject(this);
        if (budgetConfig.isFtue()) {
            startActivity(new Intent(this, FirstTimeActivity.class));
            finish();
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            float amount = budgetConfig.getBudgetAmount();
            TextView budgetAmountTextView = (TextView) findViewById(R.id.amount_text);
            if (budgetConfig.getBudgetMode() == BudgetMode.BASIC) {
                budgetAmountTextView.setText(String.format("$%.2f", amount));
            } else {
                budgetAmountTextView.setText(data.getTotalAmountString());
            }
        }

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new MonthViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(MonthViewPagerAdapter.MAX_ADAPATER_SIZE / 2, false);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_enter_purchase) {
            startActivity(new Intent(this, EnterPurchaseActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_view_all_purchases) {
            startActivity(new Intent(this, ViewAllPurchasesActivity.class));
        } else if (id == R.id.nav_edit_budget) {
            Intent i = new Intent();
            if (budgetConfig.getBudgetMode() == BudgetMode.ADVANCED) {
                // Advanced mode selected
                i.setClass(this, EditAdvancedBudgetActivity.class);
            } else {
                // Basic mode selected
                i.setClass(this, EditBasicBudgetActivity.class);
            }
            startActivity(i);
        } else if (id == R.id.nav_debug) {
            startActivity(new Intent(this, DeveloperDebugActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onResume() {
        super.onResume();
    }

}
