package com.kapirawan.financial_tracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapirawan.financial_tracker.entity.Expense;

import java.util.List;

@Dao
public interface DaoExpense {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Expense expense);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Expense[] expenses);

    @Query("SELECT * from expense")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT * from expense where accountId = :accountId")
    LiveData<List<Expense>> getAccountExpenses(long accountId);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

}