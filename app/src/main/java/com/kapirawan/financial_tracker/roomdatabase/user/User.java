package com.kapirawan.financial_tracker.roomdatabase.user;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class User {
    @PrimaryKey
    public long _id;
    public String name;
    public Date updateTimestamp;

    public User(long _id, String name, Date updateTimestamp){
        this._id = _id;
        this.name = name;
        this.updateTimestamp = updateTimestamp;
    }
}