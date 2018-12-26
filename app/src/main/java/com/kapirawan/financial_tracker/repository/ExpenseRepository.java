package com.kapirawan.financial_tracker.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.kapirawan.financial_tracker.database.roomdatabase.AppRoomDatabase;
import com.kapirawan.financial_tracker.database.roomdatabase.expense.Expense;

import java.util.List;

public class ExpenseRepository{
    AppRoomDatabase db;

    public ExpenseRepository(Application app) {
        super();
        db = AppRoomDatabase.getDatabase(app);
    }
}