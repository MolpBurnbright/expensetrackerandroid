package com.kapirawan.financial_tracker.roomdatabase.datasource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoDatasource extends DaoBase<Datasource> {

    @Query("select * from Datasource")
    List<Datasource> getAllDatasources();

    @Query("select * from Datasource where userId = :userId")
    List<Datasource> getUserDatasources(long userId);

    @Query("select * from Datasource where _id = :id")
    Datasource getDatasource(long id);

    @Query("update Datasource set lastId = :newLastId where _id = :datasourceId and lastId = :lastId")
    void updateLastId(long datasourceId, long lastId, long newLastId);

    @Query ("delete from Datasource where userId = :userId")
    void deleteUserDatasource(long userId);

    @Query("delete from Datasource")
    void deleteAllDatasources();
}