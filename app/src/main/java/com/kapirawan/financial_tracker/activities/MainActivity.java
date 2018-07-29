package com.kapirawan.financial_tracker.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.adapter.AccountAdapter;
import com.kapirawan.financial_tracker.entity.Account;
import com.kapirawan.financial_tracker.entity.User;
import com.kapirawan.financial_tracker.helper.Refresher;
import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.viewmodel.AppViewModel;
import com.kapirawan.financial_tracker.webservice.RetrofitClient;

import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity{

    private AppViewModel appViewModel = null;
    private Refresher refresher = null;
    private AppRepository appRepository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Setup the account recycler view
        onCreateSetupAccountRecyclerView();
        onCreateSetupRefresher();
    }

    private void onCreateSetupAccountRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview_account_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AccountAdapter adapter = new AccountAdapter(this, new AccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Account account) {
                Log.i("MainActivity", "Account " + account.name + "was selected.");
            }
        });
        recyclerView.setAdapter(adapter);
        //Setup the ViewModel for Account RecyclerView
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        appViewModel.getAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable List<Account> accounts) {
                adapter.setAccounts(accounts);
            }
        });
    }

    private void onCreateSetupRefresher(){
        //Set the default values of preferences for the first time.
        PreferenceManager.setDefaultValues(this, R.xml.pref_main, false);
        //Get the default shared preference, then set the token from shared preferences.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String userJSON = sharedPref.getString(getResources().getString(R.string.PREF_USER), "");
        appRepository = new AppRepository(this.getApplication());
        if(userJSON != ""){
            User user = new Gson().fromJson(userJSON, User.class);
            RetrofitClient.setToken(user.getToken());
            refresher = new Refresher(user.get_id(), appRepository);
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
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}