package com.kapirawan.financial_tracker.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.repository.ExpenseRepository;

import java.util.List;

public class ViewModelExpense extends AndroidViewModel {
    private ExpenseRepository expenseRepository;
    private LiveData<List<Expense>> expenses;

    public ViewModelExpense(Application app){
        super(app);
        expenseRepository = new ExpenseRepository(app);
    }

}
