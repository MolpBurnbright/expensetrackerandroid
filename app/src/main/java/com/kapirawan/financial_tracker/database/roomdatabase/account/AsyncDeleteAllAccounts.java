package com.kapirawan.financial_tracker.database.roomdatabase.account;

import android.os.AsyncTask;

public class AsyncDeleteAllAccounts extends AsyncTask<Void, Void, Void> {
    private DaoAccount asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncDeleteAllAccounts (DaoAccount dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.deleteAllAccounts();
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        if (listener != null)
            this.listener.onTaskCompleted();
    }

    public interface OnTaskCompleted {
        void onTaskCompleted();
    }
}