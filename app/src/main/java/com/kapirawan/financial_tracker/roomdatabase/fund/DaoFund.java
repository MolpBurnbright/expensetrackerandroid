package com.kapirawan.financial_tracker.roomdatabase.fund;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoFund extends DaoBase<Fund> {

    @Query("select * from fund where _id = :fundId and datasourceId = :datasourceId")
    Fund getFund(long fundId, long datasourceId);

    @Query("select * from fund where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    List<Fund> getAccountFunds(long accountId, long accountDatasourceId);

    @Query("select MAX(_id) from fund where datasourceId = :datasourceId")
    long getMaxId  (long datasourceId);

    @Query("select * from fund")
    List<Fund> getAllFunds();

    @Query("delete from fund")
    void deleteAllFunds();
}