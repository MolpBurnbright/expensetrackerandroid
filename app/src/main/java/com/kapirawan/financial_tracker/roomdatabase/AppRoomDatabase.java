package com.kapirawan.financial_tracker.roomdatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
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
import com.kapirawan.financial_tracker.roomdatabase.preference.DaoPreference;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;
import com.kapirawan.financial_tracker.roomdatabase.source.DaoSource;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;
import com.kapirawan.financial_tracker.roomdatabase.sum.DaoSum;
import com.kapirawan.financial_tracker.roomdatabase.user.DaoUser;
import com.kapirawan.financial_tracker.roomdatabase.user.User;

import java.util.Date;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Datasource.class, Account.class, Expense.class, Budget.class,
        Fund.class, Category.class, Source.class, Preference.class},
        version = 1, exportSchema = false)
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
    public abstract DaoSum daoSum();
    public abstract DaoPreference daoPreference();

    private static AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class,"app_database")
                            .addMigrations(new Migration_1_2())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class Migration_1_2 extends Migration {

        public Migration_1_2(){
            super(1, 2);
        }

        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `preference` (`_key` TEXT NOT NULL, "
                    + "`value` TEXT, PRIMARY KEY(`_key`))");
            database.execSQL("INSERT INTO `preference` " +
                    " VALUES ('"  + Preference.SELECTED_ACCOUNT  + "', '1,0')");
        }
    }
}