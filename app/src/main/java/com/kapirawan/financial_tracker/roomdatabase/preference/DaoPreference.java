package com.kapirawan.financial_tracker.roomdatabase.preference;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

@Dao
public interface DaoPreference extends DaoBase<Preference>{
    @Query("select * from preference where _key = :key")
    LiveData<Preference> getPrefence(String key);

    @Query("select * from preference where _key = '" + Preference.SELECTED_ACCOUNT + "'")
    LiveData<Preference> getSelectedAccount();

    @Query("select * from preference where _key = '" + Preference.SELECTED_USER + "'")
    LiveData<Preference> getSelectedUser();

}