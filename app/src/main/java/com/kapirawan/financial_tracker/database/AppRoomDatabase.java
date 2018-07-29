package com.kapirawan.financial_tracker.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.kapirawan.financial_tracker.dao.DaoAccount;
import com.kapirawan.financial_tracker.entity.Account;
import com.kapirawan.financial_tracker.helper.Converters;

@Database(entities = {Account.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract DaoAccount daoAccount();

    private static AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class,"app_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
