package com.kapirawan.financial_tracker.roomdatabase.account;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;

import java.io.Serializable;
import java.util.Date;

@Entity(primaryKeys = {"_id", "datasourceId"},
        indices = {@Index(value = {"name", "userId"}, unique = true)})

public class Account implements Serializable{

    public long _id;
    public long datasourceId;
    public long userId;
    public String name;
    public Date updateTimestamp;

    public Account(long _id, long datasourceId, long userId, String name, Date updateTimestamp){
        this._id = _id;
        this.datasourceId = datasourceId;
        this.userId = userId;
        this.name = name;
        this.updateTimestamp = updateTimestamp;
    }
}