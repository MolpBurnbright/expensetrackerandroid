package com.kapirawan.financial_tracker.ui.account;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;

import java.util.List;

public class RemoveAccountDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Account account;

    public RemoveAccountDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Account account) {
        this.account = account;
    }

    public String getAccountName(){
        return account.name;
    }

    public void removeAccount(){
        repo.deleteAccount(account, () -> {});
    }
}