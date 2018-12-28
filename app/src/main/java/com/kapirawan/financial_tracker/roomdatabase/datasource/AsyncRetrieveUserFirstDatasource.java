package com.kapirawan.financial_tracker.roomdatabase.datasource;

import android.os.AsyncTask;

public class AsyncRetrieveUserFirstDatasource extends AsyncTask<Long, Void, Datasource> {
    private DaoDatasource dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveUserFirstDatasource (DaoDatasource dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Datasource doInBackground(final Long... params) {
        return this.dao.getUserFirstDatasource(params[0]);
    }

    @Override
    protected void onPostExecute(Datasource datasource){
        if(listener != null)
            listener.onTaskCompleted(datasource);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Datasource datasource);
    }
}