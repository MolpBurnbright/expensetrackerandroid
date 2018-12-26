package com.kapirawan.financial_tracker.roomdatabase.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoUser extends DaoBase<User> {

    @Query("select * from User")
    List<User> getAllUsers();

    @Query("select * from User where _id = :id")
    User getUser(long id);

    @Query("delete from User")
    void deleteAllUsers();
}