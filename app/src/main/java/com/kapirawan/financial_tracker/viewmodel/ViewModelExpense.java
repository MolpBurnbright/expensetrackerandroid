package com.kapirawan.financial_tracker.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kapirawan.financial_tracker.entity.Account;
import com.kapirawan.financial_tracker.entity.Expense;
import com.kapirawan.financial_tracker.repository.ExpenseRepository;

import java.util.List;

public class ViewModelExpense extends AndroidViewModel {
    private ExpenseRepository expenseRepository;
    private LiveData<List<Expense>> expenses;

    public ViewModelExpense(Application app){
        super(app);
        expenseRepository = new ExpenseRepository(app);
    }

    public void init(Account account){
        if (this.expenses == null){
            expenses = expenseRepository.getAccountExpenses(account._id);
        }
    }

    public LiveData<List<Expense>> getExpenses(){
        return expenses;
    }

    public void insert(Expense expense) {
        expenseRepository.insert(expense);
    }

    public void insert(Expense[] expense){
        expenseRepository.insert(expense);
    }
}
