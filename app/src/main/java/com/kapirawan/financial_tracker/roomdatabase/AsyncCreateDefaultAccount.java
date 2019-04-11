package com.kapirawan.financial_tracker.roomdatabase;

import android.os.AsyncTask;

import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;

import java.util.Date;

// This async task will create the default account for a user
public class AsyncCreateDefaultAccount extends AsyncTask<Void, Void, Void> {
    private AppRoomDatabase db;
    private OnTaskCompleted listener;
    private long userId;
    private long datasourceId;
    private Account defaultAccount;

    public AsyncCreateDefaultAccount (long userId, long datasourceId, AppRoomDatabase database, OnTaskCompleted listener) {
        db = database;
        this.listener = listener;
        this.userId = userId;
        this.datasourceId = datasourceId;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        defaultAccount = new Account(0, datasourceId, userId,
                "Default Account", new Date());
        long accountId = db.daoAccount().insert(defaultAccount);
        defaultAccount._id = accountId;
        Category[] defaultCategories = {
                new Category(1, datasourceId, defaultAccount._id, defaultAccount.datasourceId, "Food",
                        new Date()),
                new Category(2, datasourceId, defaultAccount._id, defaultAccount.datasourceId, "Fare",
                        new Date()),
                new Category(3, datasourceId, defaultAccount._id, defaultAccount.datasourceId, "Utilities",
                        new Date()),
                new Category(4, datasourceId, defaultAccount._id, defaultAccount.datasourceId, "Others",
                        new Date()),
        };
        Source[] defaultSources = {
                new Source(1, datasourceId, defaultAccount._id, defaultAccount.datasourceId, "Salary",
                        new Date()),
                new Source(2, datasourceId, defaultAccount._id, defaultAccount.datasourceId, "Loan",
                        new Date()),
                new Source(3, datasourceId, defaultAccount._id, defaultAccount.datasourceId, "Others",
                        new Date()),
        };
        db.daoCategory().insertMultiple(defaultCategories);
        db.daoSource().insertMultiple(defaultSources);
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        if (listener != null)
            this.listener.onTaskCompleted(defaultAccount);
        db = null;
        listener = null;
        defaultAccount = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Account defaultAccount);
    }
}

