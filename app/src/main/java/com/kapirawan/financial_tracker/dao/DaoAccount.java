package com.kapirawan.financial_tracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapirawan.financial_tracker.entity.Account;

import java.util.List;

@Dao
public interface DaoAccount {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Account[] accounts);

    @Query("SELECT * from account")
    LiveData<List<Account>> getAllAccounts();

    @Update
    void updateAccount(Account account);

    @Delete
    void deleteAccount(Account account);
}