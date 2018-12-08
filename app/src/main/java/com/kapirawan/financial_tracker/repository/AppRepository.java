package com.kapirawan.financial_tracker.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.kapirawan.financial_tracker.database.AppRoomDatabase;
import com.kapirawan.financial_tracker.entity.Account;
import com.kapirawan.financial_tracker.entity.Budget;
import com.kapirawan.financial_tracker.entity.Expense;
import com.kapirawan.financial_tracker.entity.Fund;
import com.kapirawan.financial_tracker.helper.AsyncAccountInsert;
import com.kapirawan.financial_tracker.helper.AsyncAccountMultipleInsert;

import java.util.List;

public class AppRepository {
    AppRoomDatabase db;

    public AppRepository(Application app) {
        db = AppRoomDatabase.getDatabase(app);
    }

    public LiveData<List<Account>> getAccounts() {
        return db.daoAccount().getAllAccounts();
    }

    public LiveData<List<Expense>> getExpenses(long accountId) {
        return db.daoExpense().getAccountExpenses(accountId);
    }

    public LiveData<List<Budget>> getBudgets(long accountId) {
        return db.daoBudget().getAccountBudgets(accountId);
    }

    public LiveData<List<Fund>> getFunds(long accountId) {
        return db.daoFund().getFunds(accountId);
    }

    public void insert (Account account) {
        new AsyncAccountInsert(db.daoAccount()).execute(account);
    }

    public void insert (Account[] accounts) {
        new AsyncAccountMultipleInsert(db.daoAccount()).execute(accounts);
    }
}