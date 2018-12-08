package com.kapirawan.financial_tracker.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
@Entity
public class Source {
    @PrimaryKey
    @NonNull
    public long _id;
    public long accountId;
    public String name;
    public Date updateTimestamp;

    public Source(long _id, long accountId, String name, Date updateTimestamp){
        this._id = _id;
        this.accountId = accountId;
        this.name = name;
        this.updateTimestamp = updateTimestamp;
    }
}