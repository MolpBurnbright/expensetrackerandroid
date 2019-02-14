package com.kapirawan.financial_tracker.ui.fund;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddFundDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private long accountId, accountDatasourceId;
    private Date selectedDate;
    private double amount;
    private String description;
    private LiveData<List<Source>> sources;
    private LiveData<List<String>> details;
    private int selectedSourcePosition;

    public AddFundDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId){
        this.amount = 0;
        this.description = "";
        if(this.sources == null)
            this.sources = new MutableLiveData<>();
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        this.selectedSourcePosition = 0;
        this.selectedDate = Calendar.getInstance().getTime();
        setAccount(accountId, accountDatasourceId);
    }

    public void addFund(){
        Fund fund = new Fund(0, 0, accountId, accountDatasourceId,
                selectedDate, amount, getSelectedSource(), description, new Date());
        repo.createFund(fund, () -> {});
    }

    public LiveData<List<Source>> getSources() {
        return this.sources;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public long getAccountDatasourceId(){
        return this.accountDatasourceId;
    }

    public void setAccount(long accountId, long accountDatasourceId){
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        this.sources = repo.readAccountSources(accountId, accountDatasourceId);
        this.details = repo.getDetails(accountId, accountDatasourceId);
    }

    public double getAmount(){
        return this.amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public LiveData<List<String>> getDetails(){
        return this.details;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Date getSelectedDate(){
        return this.selectedDate;
    }

    public void setSelectedDate(Date date){
        this.selectedDate = date;
    }

    public String getSelectedSource(){
        return this.sources.getValue().get(this.selectedSourcePosition).name;
    }

    public int getSelectedSourcePosition(){
        return this.selectedSourcePosition;
    }

    public void setSelectedSourcePosition(int pos){
        this.selectedSourcePosition = pos;
    }
}