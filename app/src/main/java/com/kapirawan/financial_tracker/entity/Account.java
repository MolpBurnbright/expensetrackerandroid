package com.kapirawan.financial_tracker.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class Account {

    @PrimaryKey
    @NonNull
    public long _id;
    public long userId;
    public String name;
    public Date updateTimestamp;

    public Account(long _id, long userId, String name, Date updateTimestamp){
        this._id = _id;
        this.userId = userId;
        this.name = name;
        this.updateTimestamp = updateTimestamp;
    }

}