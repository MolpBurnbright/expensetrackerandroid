package com.kapirawan.financial_tracker.roomdatabase.account;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoAccount extends DaoBase<Account> {

    @Query("select * from account where _id = :accountId and datasourceId = :datasourceId")
    Account getAccount(long accountId, long datasourceId);

    @Query("select * from account where userId = :userId ")
    List<Account> getUserAccounts(long userId);

    @Query("select * from account where userId = :userId ")
    LiveData<List<Account>> getUserAccountsLD(long userId);

    @Query("select * from account")
    List<Account> getAllAccounts();

    @Query("select MAX(_id) from account where datasourceId = :datasourceId")
    long getMaxId(long datasourceId);

    @Query("delete from account")
    void deleteAllAccounts();
}