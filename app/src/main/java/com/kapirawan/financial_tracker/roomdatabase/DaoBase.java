package com.kapirawan.financial_tracker.roomdatabase;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

public interface DaoBase <T>{
    @Insert
    void insert(T obj);

    @Insert (onConflict = OnConflictStrategy.FAIL)
    void insertMultiple(T[] objs);

    @Update
    void update(T obj);

    @Delete
    void delete(T obj);
}