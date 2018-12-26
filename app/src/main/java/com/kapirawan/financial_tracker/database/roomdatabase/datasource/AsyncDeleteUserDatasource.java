package com.kapirawan.financial_tracker.database.roomdatabase.datasource;

import android.os.AsyncTask;

public class AsyncDeleteUserDatasource extends AsyncTask<Long, Void, Void> {
    private DaoDatasource asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncDeleteUserDatasource (DaoDatasource dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Long... params) {
        asyncTaskDao.deleteUserDatasource(params[0]);
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