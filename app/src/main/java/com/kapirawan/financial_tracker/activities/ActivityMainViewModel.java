package com.kapirawan.financial_tracker.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ActivityMainViewModel extends ViewModel {
    private final MutableLiveData<String> userName = new MutableLiveData<>();

    public void setUserName(String name){
        userName.setValue(name);
    }

    public LiveData<String> getUserName(){
        return userName;
    }
}