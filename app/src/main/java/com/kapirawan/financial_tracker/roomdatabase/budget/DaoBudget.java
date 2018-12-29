package com.kapirawan.financial_tracker.roomdatabase.budget;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoBudget extends DaoBase<Budget> {

    @Query("select * from budget where _id = :budgetId and datasourceId = :datasourceId")
    Budget getBudget(long budgetId, long datasourceId);

    @Query("select * from budget where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    List<Budget> getAccountBudgets(long accountId, long accountDatasourceId);

    @Query("select MAX(_id) from budget where datasourceId = :datasourceId")
    long getMaxId  (long datasourceId);

    @Query("select * from budget")
    List<Budget> getAllBudgets();

    @Query("delete from budget")
    void deleteAllBudgets();
}