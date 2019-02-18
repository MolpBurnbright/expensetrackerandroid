package com.kapirawan.financial_tracker.ui.source;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;

import java.util.List;

public class SourceFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<List<Source>> sources;
    private LiveData<Account> account;

    public SourceFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId) {
        sources = repo.readAccountSources(accountId, accountDatasourceId);
        account = repo.readAccount(accountId, accountDatasourceId);
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public LiveData<Preference> getSelectedAccount(){
        return repo.getSelectedAccount();
    }

    public LiveData<List<Source>> getSources() {
        return sources;
    }

}