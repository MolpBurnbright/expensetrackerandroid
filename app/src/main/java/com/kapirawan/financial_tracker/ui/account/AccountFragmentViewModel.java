package com.kapirawan.financial_tracker.ui.account;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.user.User;

import java.util.List;

public class AccountFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private User user;
    private LiveData<List<Account>> accounts;
    private int selectedAccount;

    public AccountFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(User user){
        this.user = user;
        accounts = repo.readUserAccounts(user._id);
    }

    public LiveData<List<Account>> getAccounts(){
        return accounts;
    }
}
