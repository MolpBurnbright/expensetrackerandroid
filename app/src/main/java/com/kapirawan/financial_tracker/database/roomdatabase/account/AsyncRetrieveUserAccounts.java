package com.kapirawan.financial_tracker.database.roomdatabase.account;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveUserAccounts extends AsyncTask<Long, Void, List<Account>> {
    private DaoAccount dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveUserAccounts (DaoAccount dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Account> doInBackground(final Long... params) {
        return this.dao.getUserAccounts(params[0]);
    }

    @Override
    protected void onPostExecute(List<Account> accounts){
        if(listener != null)
            listener.onTaskCompleted(accounts);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Account> accounts);
    }
}