package com.kapirawan.financial_tracker.roomdatabase.account;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

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