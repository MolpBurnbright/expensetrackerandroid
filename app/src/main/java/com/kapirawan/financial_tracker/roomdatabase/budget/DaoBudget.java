package com.kapirawan.financial_tracker.roomdatabase.budget;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.List;

@Dao
public interface DaoBudget extends DaoBase<Budget> {

    @Query("select * from budget where _id = :budgetId and datasourceId = :datasourceId")
    Budget getBudget(long budgetId, long datasourceId);

    @Query("select * from budget where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    List<Budget> getAccountBudgets(long accountId, long accountDatasourceId);

    @Query("select * from budget where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId " +
            "order by date desc")
    LiveData<List<Budget>> getAccountBudgetsLD (long accountId, long accountDatasourceId);

    @Query("select type as name, sum(amount) as amount from budget " +
            "where accountId = :accountId and accountDatasourceId = :accountDatasourceId " +
            "group by type")
    LiveData<List<Sum>> getAccountSumBudgets(long accountId, long accountDatasourceId);

    @Query("select MAX(_id) from budget where datasourceId = :datasourceId")
    long getMaxId  (long datasourceId);

    @Query("select * from budget")
    List<Budget> getAllBudgets();

    @Query("select sum(amount) from budget " +
            "where accountId = :accountId and accountDatasourceId = :accountDatasourceId")
    LiveData<Double> getTotalBudget(long accountId, long accountDatasourceId);

    @Query("delete from budget")
    void deleteAllBudgets();
}