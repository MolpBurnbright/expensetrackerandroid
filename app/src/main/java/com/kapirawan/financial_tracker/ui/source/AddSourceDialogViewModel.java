package com.kapirawan.financial_tracker.ui.source;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;

import java.util.Date;
import java.util.List;

public class AddSourceDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<List<Source>> sources;
    private LiveData<Preference> selectedAccount;
    private long userId;
    private long datasourceId;
    private long accountId;
    private long accountDatasourceId;

    public AddSourceDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void initAccount(long accountId, long accountDatasourceId) {
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        this.sources = repo.readAccountSources(accountId, accountDatasourceId);
    }

    public void initUserId(long userId){
        this.userId = userId;
    }

    public void initDatasourceId(long datasourceId){
        this.datasourceId = datasourceId;
    }

    public void addSource(String name){
        repo.createSource(new Source(0, datasourceId, accountId, accountDatasourceId,
                name, new Date()), () ->{});
    }

    public LiveData<List<Source>> getSources(){
        return sources;
    }

    public LiveData<Preference> getSelectedAccount(){
        if(selectedAccount == null)
            selectedAccount = repo.getSelectedAccount(userId);
        return selectedAccount;
    }
}