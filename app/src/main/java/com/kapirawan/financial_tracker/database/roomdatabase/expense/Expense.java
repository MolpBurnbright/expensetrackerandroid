package com.kapirawan.financial_tracker.database.roomdatabase.expense;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(primaryKeys = {"_id", "datasourceId", "accountId"})
public class Expense {

    @PrimaryKey (autoGenerate = true)
    public long _id;
    public long datasourceId;
    public long accountId;
    public Date date;
    public double amount;
    public String type;
    public String details;
    public Date updateTimestamp;

    public Expense(long _id, long datasourceId, long accountId, Date date, double amount, String type,
                   String details, Date updateTimestamp){
        this._id = _id;
        this.datasourceId = datasourceId;
        this.accountId = accountId;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.details = details;
        this.updateTimestamp = updateTimestamp;
    }
}