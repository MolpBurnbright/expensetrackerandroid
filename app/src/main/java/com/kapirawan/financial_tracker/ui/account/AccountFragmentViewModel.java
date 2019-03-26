package com.kapirawan.financial_tracker.ui.account;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;

import java.util.List;

public class AccountFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<List<Account>> accounts;
    private LiveData<Preference> selectedUser;
    private LiveData<Preference> selectedAccount;

    public AccountFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long userId){
        accounts = repo.readUserAccounts(userId);
        selectedAccount = repo.getSelectedAccount();
    }

    public LiveData<List<Account>> getAccounts(){
        return accounts;
    }

    public LiveData<Preference> getSelectedAccount(){
        return selectedAccount;
    }

    public LiveData<Preference> getSelectedUser(){
        if(selectedUser == null)
            selectedUser = repo.getSelectedUser();
        return selectedUser;
    }

    public void setSelectedAccount(Account account){
        String value = String.valueOf(account._id) + "," + String.valueOf(account.datasourceId);
        Preference pref = new Preference(Preference.SELECTED_ACCOUNT, value);
        repo.updatePreference(pref, () -> {});
    }
}