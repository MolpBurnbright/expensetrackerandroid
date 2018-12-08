package com.kapirawan.financial_tracker.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.kapirawan.financial_tracker.dao.DaoAccount;
import com.kapirawan.financial_tracker.dao.DaoBudget;
import com.kapirawan.financial_tracker.dao.DaoCategory;
import com.kapirawan.financial_tracker.dao.DaoExpense;
import com.kapirawan.financial_tracker.dao.DaoFund;
import com.kapirawan.financial_tracker.dao.DaoSource;
import com.kapirawan.financial_tracker.entity.Account;
import com.kapirawan.financial_tracker.entity.Budget;
import com.kapirawan.financial_tracker.entity.Category;
import com.kapirawan.financial_tracker.entity.Expense;
import com.kapirawan.financial_tracker.entity.Fund;
import com.kapirawan.financial_tracker.entity.Source;
import com.kapirawan.financial_tracker.helper.Converters;

@Database(entities = {Account.class, Expense.class, Budget.class, Fund.class, Category.class,
        Source.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract DaoAccount daoAccount();
    public abstract DaoExpense daoExpense();
    public abstract DaoBudget daoBudget();
    public abstract DaoFund daoFund();
    public abstract DaoCategory daoCategory();
    public abstract DaoSource daoSource();

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