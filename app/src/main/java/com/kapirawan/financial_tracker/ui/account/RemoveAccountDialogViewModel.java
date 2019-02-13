package com.kapirawan.financial_tracker.ui.account;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;


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

    String getAccountName(){
        return account.name;
    }

    void removeAccount(){
        repo.deleteAccount(account, () -> {});
    }
}