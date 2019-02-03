package com.kapirawan.financial_tracker.ui.budget;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;

import java.util.Date;

public class RemoveBudgetDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Budget budget;

    public RemoveBudgetDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Budget budget){
        this.budget = budget;
    }

    public void removeBudget(){
        repo.deleteBudget(this.budget,() -> {});
    }

    public double getAmount(){
        return this.budget.amount;
    }

    public String getCategory(){
        return this.budget.type;
    }

    public String getDescription(){
        return this.budget.details;
    }

    public Date getDate(){
        return this.budget.date;
    }

}
