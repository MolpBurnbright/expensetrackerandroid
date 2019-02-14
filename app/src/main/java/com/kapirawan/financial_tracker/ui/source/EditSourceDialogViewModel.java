package com.kapirawan.financial_tracker.ui.source;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;

import java.util.List;

public class EditSourceDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Source source;
    private LiveData<List<Source>> sources;

    public EditSourceDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Source source) {
        this.source = source;
        this.sources = repo.readAccountSources(source.accountId, source.accountDatasourceId);
    }

    public LiveData<List<Source>> getCategories(){
        return sources;
    }

    public String getSourceName(){
        return source.name;
    }

    public void updateSource(String name){
        source.name = name;
        repo.updateSource(source, () -> {});
    }
}