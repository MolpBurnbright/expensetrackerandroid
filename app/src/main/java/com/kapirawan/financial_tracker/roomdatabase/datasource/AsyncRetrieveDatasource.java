package com.kapirawan.financial_tracker.roomdatabase.datasource;

import android.os.AsyncTask;

public class AsyncRetrieveDatasource extends AsyncTask<Long, Void, Datasource> {
    private DaoDatasource dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveDatasource (DaoDatasource dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Datasource doInBackground(final Long... params) {
        return this.dao.getDatasource(params[0]);
    }

    @Override
    protected void onPostExecute(Datasource expense){
        if(listener != null)
            listener.onTaskCompleted(expense);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Datasource expense);
    }
}