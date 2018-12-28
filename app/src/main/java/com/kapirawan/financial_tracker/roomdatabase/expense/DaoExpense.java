package com.kapirawan.financial_tracker.roomdatabase.expense;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoExpense extends DaoBase<Expense> {

    @Query("select * from expense where _id = :expenseId and datasourceId = :datasourceId")
    Expense getExpense(long expenseId, long datasourceId);

    @Query("select * from expense where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    List<Expense> getAccountExpenses(long accountId, long accountDatasourceId);

    @Query("select MAX(_id) from expense where datasourceId = :datasourceId")
    long getMaxId(long datasourceId);

    @Query("select * from expense")
    List<Expense> getAllExpenses();

    @Query("delete from expense")
    void deleteAllExpenses();
}