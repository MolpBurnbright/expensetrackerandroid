package com.kapirawan.financial_tracker.Summary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.model.Summary;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.util.List;

public class ViewModelSummary extends AndroidViewModel {
    AppRepository repo;
    LiveData<List<Summary>> summary;

    public ViewModelSummary(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId){
        if(summary != null){
            return;
        }
        this.summary = new MutableLiveData<>();
        setAccountSummary(accountId, accountDatasourceId);
    }

    public LiveData<List<Summary>> getSummary() {
        return summary;
    }

    public void setAccountSummary(long accountId, long accountDatasourceId){
        repo.getSumAllExpenses(accountId, accountDatasourceId, sumAllExpenses -> {
            repo.getSumAllBudgets(accountId, accountDatasourceId, sumAllBudgets -> {
                List<Summary> summary = Summary.getSummaries(sumAllExpenses, sumAllBudgets);
                ((MutableLiveData<List<Summary>>) this.summary).setValue(summary);
            });
        });
    }
}