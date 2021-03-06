package com.kapirawan.financial_tracker.roomdatabase.datasource;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveUserDatasource extends AsyncTask<Long, Void, List<Datasource>> {
    private DaoDatasource dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveUserDatasource (DaoDatasource dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Datasource> doInBackground(final Long... params) {
        return this.dao.getUserDatasources(params[0]);
    }

    @Override
    protected void onPostExecute(List<Datasource> datasources){
        if(listener != null)
            listener.onTaskCompleted(datasources);
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Datasource> datasources);
    }
}