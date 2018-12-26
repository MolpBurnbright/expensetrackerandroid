package com.kapirawan.financial_tracker.roomdatabase;

import android.os.AsyncTask;

public class AsyncUpdate <T> extends AsyncTask<T, Void, Void> {
    private DaoBase asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncUpdate (DaoBase dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final T... params) {
        asyncTaskDao.update(params[0]);
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