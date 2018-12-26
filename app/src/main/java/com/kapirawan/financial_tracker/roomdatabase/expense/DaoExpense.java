package com.kapirawan.financial_tracker.roomdatabase.expense;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoExpense extends DaoBase<Expense> {

    @Query("SELECT * from expense where _id = :expenseId")
    Expense getExpense(long expenseId);

    @Query("SELECT * from expense where accountId = :accountId")
    List<Expense> getAccountExpenses(long accountId);

    @Query("SELECT * from expense")
    List<Expense> getAllExpenses();

    @Query("delete from expense")
    void deleteAllExpenses();
}