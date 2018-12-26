package com.kapirawan.financial_tracker.database.roomdatabase.datasource;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Datasource {
    @PrimaryKey
    public long _id;
    public long userId;
    public String name;
    public long lastId;
    public Date updateTimestamp;

    public Datasource(long _id, long userId, String name, long lastId, Date updateTimestamp){
        this._id = _id;
        this.userId = userId;
        this.name = name;
        this.lastId = lastId;
        this.updateTimestamp = updateTimestamp;
    }
}