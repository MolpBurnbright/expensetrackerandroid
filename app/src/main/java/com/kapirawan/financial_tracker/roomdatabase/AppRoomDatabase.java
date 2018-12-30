package com.kapirawan.financial_tracker.roomdatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.account.DaoAccount;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;
import com.kapirawan.financial_tracker.roomdatabase.budget.DaoBudget;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.category.DaoCategory;
import com.kapirawan.financial_tracker.roomdatabase.datasource.DaoDatasource;
import com.kapirawan.financial_tracker.roomdatabase.datasource.Datasource;
import com.kapirawan.financial_tracker.roomdatabase.expense.DaoExpense;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.roomdatabase.fund.DaoFund;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;
import com.kapirawan.financial_tracker.roomdatabase.source.DaoSource;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;
import com.kapirawan.financial_tracker.roomdatabase.user.DaoUser;
import com.kapirawan.financial_tracker.roomdatabase.user.User;

import java.util.Date;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Datasource.class, Account.class, Expense.class, Budget.class,
        Fund.class, Category.class, Source.class},
        version = 1)
@TypeConverters({Converters.class})
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract DaoUser daoUser();
    public abstract DaoDatasource daoDatasource();
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
                            AppRoomDatabase.class,"app_database")
                            .addCallback(getCallback(context))
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback getCallback(Context context){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadScheduledExecutor().execute(() -> addDefaultData(context));
            }
        };
    }

    //Adding default data
    private static void addDefaultData(Context context){
        User defaultUser = new User(0, "Default User", new Date());
        Datasource defaultDatasource = new Datasource(0, 0,
                "Default datasource", new Date());
        Account defaultAccount = new Account(1, 0, 0,
                "Default Account", new Date());
        Category[] defaultCategories = {
                new Category(1, 0, 1, 0, "Food",
                        new Date()),
                new Category(2, 0, 1, 0, "Fare",
                        new Date()),
                new Category(3, 0, 1, 0, "Utilities",
                        new Date()),
                new Category(4, 0, 1, 0, "Others",
                        new Date()),
        };
        Source[] defaultSources = {
                new Source(1, 0, 1, 0, "Salary",
                        new Date()),
                new Source(2, 0, 1, 0, "Loan",
                        new Date()),
                new Source(3, 0, 1, 0, "Others",
                        new Date()),
        };
        AppRoomDatabase db = getDatabase(context);
        db.daoUser().insert(defaultUser);
        db.daoDatasource().insert(defaultDatasource);
        db.daoAccount().insert(defaultAccount);
        db.daoCategory().insertMultiple(defaultCategories);
        db.daoSource().insertMultiple(defaultSources);
    }
}