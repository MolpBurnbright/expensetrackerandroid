package com.kapirawan.financial_tracker.roomdatabase.category;

import android.os.AsyncTask;

public class AsyncDeleteAllCategories extends AsyncTask<Void, Void, Void> {
    private DaoCategory asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncDeleteAllCategories(DaoCategory dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.deleteAllCategories();
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        if (listener != null)
            this.listener.onTaskCompleted();
        asyncTaskDao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted();
    }
}