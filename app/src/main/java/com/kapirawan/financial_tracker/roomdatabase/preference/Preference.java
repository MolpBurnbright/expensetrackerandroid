package com.kapirawan.financial_tracker.roomdatabase.preference;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"userId", "_key"})
public class Preference {
    public long userId;
    @NonNull
    public String _key;
    public String value;

    public Preference(long userId, @NonNull String key, String value){
        this.userId = userId;
        this._key = key;
        this.value = value;
    }

    public static final String SELECTED_ACCOUNT = "Preference.SELECTED_ACCOUNT";
    public static final String SELECTED_USER = "Preference.SELECTED_USER";
}
