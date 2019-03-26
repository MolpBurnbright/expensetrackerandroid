package com.kapirawan.financial_tracker.roomdatabase.expense;

import android.arch.persistence.room.Entity;

import java.util.Date;

@Entity (primaryKeys = {"_id", "datasourceId"})
public class Expense {

    public long _id;
    public long datasourceId;
    public long accountId;
    public long accountDatasourceId;
    public Date date;
    public double amount;
    public String type;
    public String details;
    public Date updateTimestamp;

    public Expense(long _id, long datasourceId, long accountId, long accountDatasourceId, Date date,
                   double amount, String type, String details, Date updateTimestamp){
        this._id = _id;
        this.datasourceId = datasourceId;
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.details = details;
        this.updateTimestamp = updateTimestamp;
    }
}