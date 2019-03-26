package com.kapirawan.financial_tracker.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.ui.account.AccountFragment;
import com.kapirawan.financial_tracker.ui.main.FragmentMain;

public class ActivityMain extends AppCompatActivity{

    FragmentMain mFragmentMain;
    AccountFragment mAccountFragment;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userName = "";
        if(firebaseUser != null)
            userName = firebaseUser.getDisplayName();
        ViewModelProviders.of(this).get(ActivityMainViewModel.class).setUserName(userName);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(userName != "")
            toolbar.setTitle("Hi " + userName + " !");
        else
            toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null)
                return;
            mFragmentMain = new FragmentMain();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    mFragmentMain).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction;
        switch (item.getItemId()){
            case R.id.action_home:
                if(mFragmentMain == null)
                    mFragmentMain = new FragmentMain();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, mFragmentMain);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.action_accounts:
                if (mAccountFragment == null)
                    mAccountFragment = new AccountFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, mAccountFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.action_signin:
                startActivity(new Intent(this, ActivitySignIn.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, ActivitySettings.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}