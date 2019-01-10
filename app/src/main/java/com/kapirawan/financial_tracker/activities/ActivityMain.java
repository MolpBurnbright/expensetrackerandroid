package com.kapirawan.financial_tracker.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.adapter.AccountAdapter;
import com.kapirawan.financial_tracker.expense.AddExpenseDialog;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.helper.Refresher;
import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.summary.FragmentSummary;
import com.kapirawan.financial_tracker.testing.AccountTesting;
import com.kapirawan.financial_tracker.testing.BudgetTesting;
import com.kapirawan.financial_tracker.testing.CategoryTesting;
import com.kapirawan.financial_tracker.testing.DatasourceTesting;
import com.kapirawan.financial_tracker.testing.DefaultDataTesting;
import com.kapirawan.financial_tracker.testing.ExpenseTesting;
import com.kapirawan.financial_tracker.testing.FundTesting;
import com.kapirawan.financial_tracker.testing.SumTesting;
import com.kapirawan.financial_tracker.testing.UserTesting;
import com.kapirawan.financial_tracker.testing.ViewModelSummaryTesting;
import com.kapirawan.financial_tracker.viewmodel.ViewModelAccount;

import java.util.List;

public class ActivityMain extends AppCompatActivity{

    private ViewModelAccount viewModelAccount = null;
    private Refresher refresher = null;
    private AppRepository appRepository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setup the content layout
        setContentView(R.layout.activity_main);
        //Setup the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Setup the Snackbar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Setup the  account recycler view
//        onCreateSetupAccountRecyclerView();
        //Setup the refresher
        //Setup the refresher
        refresher = new Refresher(this.getApplication());
        appRepository = AppRepository.getInstance(this.getApplication());
        //Testing - including setting up the test data
//        testing();

        //Setup the fragment
        if (findViewById(R.id.framelayout_container) != null){
            if(savedInstanceState != null)
                return;
            FragmentSummary fragment = new FragmentSummary();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout_container, fragment).commit();
        }
    }

    private void onCreateSetupAccountRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview_account_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AccountAdapter adapter = new AccountAdapter(this, new AccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Account account) {
                Log.i("ActivityMain", "Account " + account.name + "was selected.");
                Intent intent = new Intent(getApplicationContext(), ActivityAccountOptions.class);
                intent.putExtra("Account", account);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        //Setup the ViewModel for Account RecyclerView
        viewModelAccount = ViewModelProviders.of(this).get(ViewModelAccount.class);
        viewModelAccount.getAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable List<Account> accounts) {
                adapter.setAccounts(accounts);
            }
        });
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
            case R.id.action_add_expense:
                new AddExpenseDialog().show(getSupportFragmentManager(), "Add Expense Dialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void testing() {
        //new UserTesting(appRepository).test(() -> {
        //    new AccountTesting(appRepository).test(() ->
        //            new ExpenseTesting(appRepository).test(() -> {})
        //    );
        //});
        //new DatasourceTesting(appRepository).test(() -> {});
        //new BudgetTesting(appRepository).test(() -> {});
        //new FundTesting(appRepository).test(() -> {});
        //new CategoryTesting(appRepository).test(() ->
        //        new DatasourceTesting(appRepository).test(() -> {}));
        //new DefaultDataTesting(appRepository).test(() -> {});
        //new SumTesting(appRepository).test(() ->{});
        new ViewModelSummaryTesting(this).test(() -> {});
    }
}