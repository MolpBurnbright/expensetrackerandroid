package com.kapirawan.financial_tracker.roomdatabase;

import android.os.AsyncTask;

public class AsyncInsert<T>  extends AsyncTask<T, Void, Long> {
    private DaoBase asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncInsert(DaoBase dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Long doInBackground(final T... params) {
        return asyncTaskDao.insert(params[0]);
    }

    @Override
    protected void onPostExecute(Long id){
        if (listener != null)
            this.listener.onTaskCompleted(id);
        asyncTaskDao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(long id);
    }
}