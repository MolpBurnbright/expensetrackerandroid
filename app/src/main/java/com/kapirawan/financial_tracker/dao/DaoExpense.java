package com.kapirawan.financial_tracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapirawan.financial_tracker.entity.Expense;

import java.util.List;

public interface DaoExpense {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Expense expense);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Expense[] expenses);

    @Query("SELECT * from account")
    LiveData<List<Expense>> getAllExpenses();

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

}