package com.kapirawan.financial_tracker.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.util.List;

public class ViewModelAccount extends AndroidViewModel {
    private AppRepository appRepository;
    private LiveData<List<Account>> accounts;

    public ViewModelAccount(Application app){
        super(app);
//        appRepository = new AppRepository(app);
//        accounts = appRepository.getAccounts();
    }

    public LiveData<List<Account>> getAccounts(){
        return accounts;
    }

/*    public void insert(Account account) {
        appRepository.insert(account);
    }

    public void insert(Account[] accounts){
        appRepository.insert(accounts);
    }
*/
}