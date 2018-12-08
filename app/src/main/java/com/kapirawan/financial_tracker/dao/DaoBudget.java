package com.kapirawan.financial_tracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapirawan.financial_tracker.entity.Budget;

import java.util.List;
@Dao
public interface DaoBudget {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Budget budget);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Budget[] budgets);

    @Query("SELECT * from budget")
    LiveData<List<Budget>> getAllBudgets();

    @Query("SELECT * from budget where accountId = :accountId")
    LiveData<List<Budget>> getAccountBudgets(long accountId);

    @Update
    void updateBudget(Budget budget);

    @Delete
    void deleteBudget(Budget budget);

}