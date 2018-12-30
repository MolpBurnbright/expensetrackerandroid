package com.kapirawan.financial_tracker.roomdatabase.source;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoSource extends DaoBase<Source> {

    @Query("select * from source where _id = :sourceId and datasourceId = :datasourceId")
    Source getSource(long sourceId, long datasourceId);

    @Query("select * from source where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    List<Source> getAccountSources(long accountId, long accountDatasourceId);

    @Query("select * from source")
    List<Source> getAllSources();

    @Query("select MAX(_id) from source where datasourceId = :datasourceId")
    long getMaxId(long datasourceId);

    @Query("delete from source")
    void deleteAllSources();
}