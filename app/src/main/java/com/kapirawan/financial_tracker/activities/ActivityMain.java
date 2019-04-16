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
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.ui.account.AccountFragment;
import com.kapirawan.financial_tracker.ui.main.FragmentMain;
import com.kapirawan.financial_tracker.ui.settings.SettingsFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActivityMain extends AppCompatActivity{
    String TAG = "ActivityMain";
    FragmentMain mFragmentMain;
    AccountFragment mAccountFragment;
    SettingsFragment mSettingsFragment;
    GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private AppRepository appRepository;
    private ActivityMainViewModel viewModel;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ActivityMainViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        appRepository = AppRepository.getInstance(this.getApplication());
        if(firebaseUser == null) {
            openSignInActivity();
            return;
        }
        userName = firebaseUser.getDisplayName();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {
                    Log.d(TAG, "onConnectionFailed:" + connectionResult);
                    Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        viewModel.setGoogleApiClient(googleApiClient);
        viewModel.setFirebaseAuth(firebaseAuth);
        viewModel.setFirebaseUser(firebaseUser);
        onCreateGetUserData(firebaseUser.getEmail());
    }

    private void onCreateGetUserData(String userId){
        //Retrieve the user data from local database. If it is the first time that the user have logged in.
        appRepository.readUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                        viewModel.setUser(user);
                        appRepository.readUserFirstDatasource(user._id, datasource -> {
                            viewModel.setDatasource(datasource);
                            onCreateSetViewLayout();
                        });
                    },
                    error -> {
                        openSignInActivity();
                    }
                );
    }

    private void onCreateSetViewLayout(){
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(userName != "")
            toolbar.setTitle("Hi " + userName + " !");
        else
            toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (findViewById(R.id.fragment_container) != null){
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
            case R.id.action_settings:
                if(mSettingsFragment == null)
                    mSettingsFragment = new SettingsFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, mSettingsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.action_signout:
                firebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(googleApiClient);
                startActivity(new Intent(this, ActivitySignIn.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Open the Sign In Activity to ask used to login
    private void openSignInActivity(){
        startActivity(new Intent(this, ActivitySignIn.class));
        finish();
    }
}