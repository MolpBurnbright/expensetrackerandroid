package com.kapirawan.financial_tracker.database.roomdatabase.account;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapirawan.financial_tracker.database.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoAccount extends DaoBase<Account> {

    @Query("select * from account where _id = :accountId")
    Account getAccount(long accountId);

    @Query("select * from account where userId = :userId ")
    List<Account> getUserAccounts(long userId);

    @Query("select * from account")
    List<Account> getAllAccounts();

    @Query("delete from account")
    void deleteAllAccounts();
}