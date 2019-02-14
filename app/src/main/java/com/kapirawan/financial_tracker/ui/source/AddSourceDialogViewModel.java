package com.kapirawan.financial_tracker.ui.source;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;

import java.util.Date;
import java.util.List;

public class AddSourceDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Account account;
    private LiveData<List<Source>> sources;

    public AddSourceDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Account account) {
        this.account = account;
        this.sources = repo.readAccountSources(account._id, account.datasourceId);
    }

    public LiveData<List<Source>> getCategories(){
        return sources;
    }

    public void addSource(String name){
        repo.createSource(new Source(0, 0, account._id, account.datasourceId,
                name, new Date()), () ->{});
    }
}