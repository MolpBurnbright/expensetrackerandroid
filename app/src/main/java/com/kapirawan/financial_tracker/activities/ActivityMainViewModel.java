package com.kapirawan.financial_tracker.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapirawan.financial_tracker.roomdatabase.datasource.Datasource;
import com.kapirawan.financial_tracker.roomdatabase.user.User;

public class ActivityMainViewModel extends ViewModel {
    private User user;
    private Datasource datasource;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public Datasource getDatasource(){
        return datasource;
    }

    public FirebaseAuth getFirebaseAuth(){
        return firebaseAuth;
    }

    public FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public GoogleApiClient getGoogleApiClient(){
        return googleApiClient;
    }

    public User getUser(){
        return user;
    }

    public void setDatasource(Datasource datasource){
        this.datasource = datasource;
    }

    public void setFirebaseAuth(FirebaseAuth auth){
        firebaseAuth = auth;
    }

    public void setFirebaseUser(FirebaseUser user){
        firebaseUser = user;
    }

    public void setGoogleApiClient(GoogleApiClient apiClient){
        googleApiClient = apiClient;
    }

    public void setUser(User user){
        this.user = user;
    }

}