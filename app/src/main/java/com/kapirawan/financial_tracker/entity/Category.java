package com.kapirawan.financial_tracker.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
@Entity
public class Category {
    @PrimaryKey
    @NonNull
    public long _id;
    public long datasourceId;
    public long accountId;
    public String name;
    public Date updateTimestamp;

    public Category(long _id, long datasourceId, long accountId, String name, Date updateTimestamp){
        this._id = _id;
        this.datasourceId = datasourceId;
        this.accountId = accountId;
        this.name = name;
        this.updateTimestamp = updateTimestamp;
    }
}
