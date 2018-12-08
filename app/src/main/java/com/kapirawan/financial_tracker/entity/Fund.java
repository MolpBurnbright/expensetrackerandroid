package com.kapirawan.financial_tracker.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
@Entity
public class Fund {
    @PrimaryKey
    @NonNull
    public long _id;
    public long accountId;
    public Date date;
    public double amount;
    public String source;
    public String details;
    public Date updateTimestamp;

    public Fund(long _id, long accountId, Date date, double amount, String source,
                   String details, Date updateTimestamp){
        this._id = _id;
        this.accountId = accountId;
        this.date = date;
        this.amount = amount;
        this.source = source;
        this.details = details;
        this.updateTimestamp = updateTimestamp;
    }
}
