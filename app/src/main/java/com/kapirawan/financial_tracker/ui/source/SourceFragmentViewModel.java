package com.kapirawan.financial_tracker.ui.source;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;

import java.util.List;

public class SourceFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Account account;
    private LiveData<List<Source>> sources;

    public SourceFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Account account) {
        this.account = account;
        sources = repo.readAccountSources(account._id, account.datasourceId);
    }

    public LiveData<List<Source>> getCategories() {
        return sources;
    }
}