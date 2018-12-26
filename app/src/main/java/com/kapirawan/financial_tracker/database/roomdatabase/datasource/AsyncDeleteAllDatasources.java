package com.kapirawan.financial_tracker.database.roomdatabase.datasource;

import android.os.AsyncTask;

public class AsyncDeleteAllDatasources  extends AsyncTask<Void, Void, Void> {
    private DaoDatasource asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncDeleteAllDatasources (DaoDatasource dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.deleteAllDatasources();
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
