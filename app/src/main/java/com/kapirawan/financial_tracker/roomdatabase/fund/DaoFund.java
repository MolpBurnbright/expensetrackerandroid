package com.kapirawan.financial_tracker.roomdatabase.fund;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.List;

@Dao
public interface DaoFund extends DaoBase<Fund> {

    @Query("select * from fund where _id = :fundId and datasourceId = :datasourceId")
    Fund getFund(long fundId, long datasourceId);

    @Query("select * from fund where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    List<Fund> getAccountFunds(long accountId, long accountDatasourceId);

    @Query("select * from fund where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId " +
            "order by date desc")
    LiveData<List<Fund>> getAccountFundsLD (long accountId, long accountDatasourceId);

    @Query("select type as name, sum(amount) as amount from fund " +
            "where accountId = :accountId and accountDatasourceId = :accountDatasourceId " +
            "group by type")
    LiveData<List<Sum>> getAccountSumFunds(long accountId, long accountDatasourceId);

    @Query("select MAX(_id) from fund where datasourceId = :datasourceId")
    long getMaxId  (long datasourceId);

    @Query("select * from fund")
    List<Fund> getAllFunds();

    @Query("select sum(amount) from fund " +
            "where accountId = :accountId and accountDatasourceId = :accountDatasourceId")
    LiveData<Double> getTotalFund(long accountId, long accountDatasourceId);

    @Query("delete from fund")
    void deleteAllFunds();
}