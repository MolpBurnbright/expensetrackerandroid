package com.kapirawan.financial_tracker.ui.fund;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;

import java.util.Date;
import java.util.List;

public class EditFundDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Fund fund;
    private LiveData<List<Source>> sources;
    private LiveData<List<String>> details;

    public EditFundDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Fund fund){
        this.fund = fund;
        this.sources = repo.readAccountSources(fund.accountId, fund.accountDatasourceId);
        this.details = repo.getDetails(fund.accountId, fund.accountDatasourceId);
    }

    public double getAmount(){
        return this.fund.amount;
    }

    public Date getDate(){
        return this.fund.date;
    }

    public String getDescription(){
        return this.fund.details;
    }

    public LiveData<List<String>> getDetails(){
        return this.details;
    }

    public String getSource(){
        return this.fund.type;
    }

    public LiveData<List<Source>> getSources() {
        return this.sources;
    }

    public void setAmount(double amount){
        this.fund.amount = amount;
    }

    public void setDescription(String description){
        this.fund.details = description;
    }

    public void setDate(Date date){
        this.fund.date = date;
    }

    public void setSource(String source){
        this.fund.type = source;
    }

    public void updateFund(){
        repo.updateFund(this.fund, () -> {});
    }

}
