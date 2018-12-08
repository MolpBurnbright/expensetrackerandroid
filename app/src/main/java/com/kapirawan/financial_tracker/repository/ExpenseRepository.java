package com.kapirawan.financial_tracker.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.kapirawan.financial_tracker.database.AppRoomDatabase;
import com.kapirawan.financial_tracker.entity.Expense;
import com.kapirawan.financial_tracker.helper.AsyncExpenseInsert;
import com.kapirawan.financial_tracker.helper.AsyncExpenseMultipleInsert;

import java.util.List;

public class ExpenseRepository{
    AppRoomDatabase db;

    public ExpenseRepository(Application app) {
        super();
        db = AppRoomDatabase.getDatabase(app);
    }

    public LiveData<List<Expense>> getExpenses(){
        return db.daoExpense().getAllExpenses();
    }

    public LiveData<List<Expense>> getAccountExpenses(long accountId){
        return db.daoExpense().getAccountExpenses(accountId);
    }


    public void insert (Expense expense){
        new AsyncExpenseInsert(db.daoExpense()).execute(expense);
    }

    public void insert (Expense[] expenses){
        new AsyncExpenseMultipleInsert(db.daoExpense()).execute(expenses);
    }
}