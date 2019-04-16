package com.kapirawan.financial_tracker.roomdatabase.preference;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

@Dao
public interface DaoPreference extends DaoBase<Preference>{
    @Query("select * from preference where userId = :userId and _key = :key")
    LiveData<Preference> getPrefence(long userId, String key);

    @Query("select * from preference where userId = :userId and _key = '" + Preference.SELECTED_ACCOUNT + "'")
    LiveData<Preference> getSelectedAccount(long userId);

    @Query("select * from preference where userId = :userId and _key = '" + Preference.SELECTED_USER + "'")
    LiveData<Preference> getSelectedUser(long userId);

    @Update
    void update(Preference pref);

}