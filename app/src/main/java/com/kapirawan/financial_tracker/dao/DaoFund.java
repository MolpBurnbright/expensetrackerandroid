package com.kapirawan.financial_tracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapirawan.financial_tracker.entity.Fund;

import java.util.List;
@Dao
public interface DaoFund {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Fund fund);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Fund[] funds);

    @Query("SELECT * from fund")
    LiveData<List<Fund>> getAllFunds();

    @Query("SELECT * from fund where accountId = :accountId")
    LiveData<List<Fund>> getFunds(long accountId);

    @Update
    void updateFund(Fund fund);

    @Delete
    void deleteFund(Fund fund);

}