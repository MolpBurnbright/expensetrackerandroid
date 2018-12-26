package com.kapirawan.financial_tracker.database.roomdatabase.account;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAllAccounts extends AsyncTask<Void, Void, List<Account>> {
    private DaoAccount dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAllAccounts (DaoAccount dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Account> doInBackground(final Void... params) {
        return this.dao.getAllAccounts();
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