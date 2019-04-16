package com.kapirawan.financial_tracker.ui.account;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.user.User;

import java.util.Date;
import java.util.List;

public class AddAccountDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private long userId;
    private long datasource;
    private LiveData<List<Account>> accounts;

    public AddAccountDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long userId, long datasource) {
        this.userId = userId;
        this.datasource = datasource;
        this.accounts = repo.readUserAccounts(userId);
    }

    public LiveData<List<Account>> getAccounts(){
        return accounts;
    }

    public void addAccount(String name){
        repo.createAccount(new Account(0, datasource, userId, name, new Date()), () ->{});
    }
}