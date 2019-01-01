package com.kapirawan.financial_tracker.roomdatabase.sum;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoSum {

    @Query("select type as name, sum(amount) as amount from expense " +
            "where accountId = :accountId and accountDatasourceId = :accountDatasourceId " +
            "group by type")
    List<Sum> getSumAllExpenses(long accountId, long accountDatasourceId);

    @Query("select type as name, sum(amount) as amount from budget " +
            "where accountId = :accountId and accountDatasourceId = :accountDatasourceId " +
            "group by type")
    List<Sum> getSumAllBudgets(long accountId, long accountDatasourceId);

    @Query("select type as name, sum(amount) as amount from fund " +
            "where accountId = :accountId and accountDatasourceId = :accountDatasourceId " +
            "group by type")
    List<Sum> getSumAllFunds(long accountId, long accountDatasourceId);

}
