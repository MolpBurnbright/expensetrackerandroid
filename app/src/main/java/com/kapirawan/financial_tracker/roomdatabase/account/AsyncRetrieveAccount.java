package com.kapirawan.financial_tracker.roomdatabase.account;

import android.os.AsyncTask;

public class AsyncRetrieveAccount extends AsyncTask<Long, Void, Account> {
    private DaoAccount dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAccount (DaoAccount dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Account doInBackground(final Long... params) {
        return this.dao.getAccount(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(Account account){
        if(listener != null)
            listener.onTaskCompleted(account);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Account account);
    }
}