package com.kapirawan.financial_tracker.roomdatabase.user;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {
    @PrimaryKey
    public long _id;
    public String userName;
    public Date updateTimestamp;

    public User(long _id, String userName, Date updateTimestamp){
        this._id = _id;
        this.userName = userName;
        this.updateTimestamp = updateTimestamp;
    }
}