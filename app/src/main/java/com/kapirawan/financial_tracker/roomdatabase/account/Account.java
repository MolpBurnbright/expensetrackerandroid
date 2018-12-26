package com.kapirawan.financial_tracker.roomdatabase.account;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Account implements Serializable{

    @PrimaryKey(autoGenerate = true)
    public long _id;
    public long userId;
    public long datasourceId;
    public String name;
    public Date updateTimestamp;

    public Account(long _id, long userId, long datasourceId, String name, Date updateTimestamp){
        this._id = _id;
        this.userId = userId;
        this.datasourceId = datasourceId;
        this.name = name;
        this.updateTimestamp = updateTimestamp;
    }
}