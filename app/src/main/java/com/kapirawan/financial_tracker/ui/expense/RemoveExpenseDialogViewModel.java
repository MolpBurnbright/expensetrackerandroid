package com.kapirawan.financial_tracker.ui.expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

import java.util.Date;

public class RemoveExpenseDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Expense expense;

    public RemoveExpenseDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Expense expense){
        this.expense = expense;
    }

    public void removeExpense(){
        repo.deleteExpense(this.expense,() -> {});
    }

    public double getAmount(){
        return this.expense.amount;
    }

    public String getCategory(){
        return this.expense.type;
    }

    public String getDescription(){
        return this.expense.details;
    }

    public Date getDate(){
        return this.expense.date;
    }

}
