package com.kapirawan.financial_tracker.roomdatabase.source;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;

import java.io.Serializable;
import java.util.Date;

@Entity(primaryKeys = {"_id", "datasourceId"},
        indices = {@Index(value = {"name", "accountId"}, unique = true)})

public class Source implements Serializable{

    public long _id;
    public long datasourceId;
    public long accountId;
    public long accountDatasourceId;
    public String name;
    public Date updateTimestamp;

    public Source(long _id, long datasourceId, long accountId, long accountDatasourceId,
                  String name, Date updateTimestamp){
        this._id = _id;
        this.datasourceId = datasourceId;
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        this.name = name;
        this.updateTimestamp = updateTimestamp;
    }
}