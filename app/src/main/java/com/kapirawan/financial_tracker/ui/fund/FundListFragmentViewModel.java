package com.kapirawan.financial_tracker.ui.fund;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;

import java.util.List;

public class FundListFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<Preference> selectedAccount;
    private LiveData<List<Fund>> funds;
    private LiveData<Account> account;

    public FundListFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId) {
        account = repo.readAccount(accountId, accountDatasourceId);
        funds = repo.readAccountFunds(accountId, accountDatasourceId);
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public LiveData<List<Fund>> getFunds() {
        return funds;
    }

    public LiveData<Preference> getSelectedAccount(){
        if(selectedAccount == null)
            selectedAccount = repo.getSelectedAccount();
        return selectedAccount;
    }
}