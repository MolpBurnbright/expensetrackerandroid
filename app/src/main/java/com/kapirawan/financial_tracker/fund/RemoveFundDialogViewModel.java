package com.kapirawan.financial_tracker.fund;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;

import java.util.Date;

public class RemoveFundDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Fund fund;

    public RemoveFundDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Fund fund){
        this.fund = fund;
    }

    public void removeFund(){
        repo.deleteFund(this.fund,() -> {});
    }

    public double getAmount(){
        return this.fund.amount;
    }

    public String getSource(){
        return this.fund.type;
    }

    public String getDescription(){
        return this.fund.details;
    }

    public Date getDate(){
        return this.fund.date;
    }

}
