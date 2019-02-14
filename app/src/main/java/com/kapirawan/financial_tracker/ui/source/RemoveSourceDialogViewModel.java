package com.kapirawan.financial_tracker.ui.source;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;


public class RemoveSourceDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Source source;

    public RemoveSourceDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Source source) {
        this.source = source;
    }

    public String getSourceName(){
        return source.name;
    }

    public void removeSource(){
        repo.deleteSource(source, () -> {});
    }
}