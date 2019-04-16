package com.kapirawan.financial_tracker.roomdatabase;

import android.os.AsyncTask;

public class AsyncInsert<T>  extends AsyncTask<T, Void, Void> {
    private DaoBase asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncInsert(DaoBase dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final T... params) {
        asyncTaskDao.insert(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void  v){
        if (listener != null)
            this.listener.onTaskCompleted();
        asyncTaskDao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted();
    }
}