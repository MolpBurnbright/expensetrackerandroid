package com.kapirawan.financial_tracker.roomdatabase.expense;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.List;

@Dao
public interface DaoExpense extends DaoBase<Expense> {

    @Query("select * from expense where _id = :expenseId and datasourceId = :datasourceId")
    Expense getExpense(long expenseId, long datasourceId);

    @Query("select * from expense where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    List<Expense> getAccountExpenses(long accountId, long accountDatasourceId);

    @Query("select * from expense where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId " +
            "order by date desc")
    LiveData<List<Expense>> getAccountExpensesLD (long accountId, long accountDatasourceId);

    @Query("select distinct(details) from expense where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    LiveData<List<String>> getDetails(long accountId, long accountDatasourceId);

    @Query("select type as name, sum(amount) as amount from expense " +
            "where accountId = :accountId and accountDatasourceId = :accountDatasourceId " +
            "group by type")
    LiveData<List<Sum>> getAccountSumExpenses(long accountId, long accountDatasourceId);

    @Query("select MAX(_id) from expense where datasourceId = :datasourceId")
    long getMaxId  (long datasourceId);

    @Query("select * from expense")
    List<Expense> getAllExpenses();

    @Query("delete from expense")
    void deleteAllExpenses();
}