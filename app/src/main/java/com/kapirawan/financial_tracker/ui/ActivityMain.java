package com.kapirawan.financial_tracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.activities.ActivitySettings;
import com.kapirawan.financial_tracker.ui.budget.BudgetListFragment;
import com.kapirawan.financial_tracker.ui.expense.ExpenseListFragment;
import com.kapirawan.financial_tracker.ui.fund.FundListFragment;
import com.kapirawan.financial_tracker.helper.Refresher;
import com.kapirawan.financial_tracker.ui.summary.FragmentSummary;

public class ActivityMain extends AppCompatActivity{

    private Refresher refresher = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setup the content layout
        setContentView(R.layout.activity_main);
        //Setup the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Setup the refresher
        refresher = new Refresher(this.getApplication());

        //Setup the fragment
        if (findViewById(R.id.framelayout_container) != null){
            if(savedInstanceState != null)
                return;
            FragmentSummary fragment = new FragmentSummary();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout_container, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                if(this.refresher != null)
                    this.refresher.refresh();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, ActivitySettings.class);
                startActivity(intent);
                break;
            case R.id.action_show_expenses:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout_container, new ExpenseListFragment()).commit();
                break;
            case R.id.action_show_budgets:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout_container, new BudgetListFragment()).commit();
                break;
            case R.id.action_show_funds:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout_container, new FundListFragment()).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}