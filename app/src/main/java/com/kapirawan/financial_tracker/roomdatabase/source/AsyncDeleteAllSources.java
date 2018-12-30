package com.kapirawan.financial_tracker.roomdatabase.source;

import android.os.AsyncTask;

public class AsyncDeleteAllSources extends AsyncTask<Void, Void, Void> {
    private DaoSource asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncDeleteAllSources(DaoSource dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.deleteAllSources();
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