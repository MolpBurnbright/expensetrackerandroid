package com.kapirawan.financial_tracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapirawan.financial_tracker.entity.Source;

import java.util.List;

@Dao
public interface DaoSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Source source);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Source[] sources);

    @Query("SELECT * from source")
    LiveData<List<Source>> getAllSources();

    @Query("SELECT * from source where accountId = :accountId")
    LiveData<List<Source>> getAccountSources(long accountId);

    @Update
    void updateSource(Source source);

    @Delete
    void deleteSource(Source source);

}
