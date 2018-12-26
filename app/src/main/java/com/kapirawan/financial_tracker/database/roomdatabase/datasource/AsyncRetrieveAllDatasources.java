package com.kapirawan.financial_tracker.database.roomdatabase.datasource;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAllDatasources extends AsyncTask<Void, Void, List<Datasource>> {
    private DaoDatasource dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAllDatasources(DaoDatasource dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Datasource> doInBackground(final Void... params) {
        return this.dao.getAllDatasources();
    }

    @Override
    protected void onPostExecute(List<Datasource> datasources){
        if(listener != null)
            listener.onTaskCompleted(datasources);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Datasource> datasources);
    }
}