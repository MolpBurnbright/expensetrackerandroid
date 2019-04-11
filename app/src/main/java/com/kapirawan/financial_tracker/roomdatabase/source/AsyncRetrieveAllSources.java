package com.kapirawan.financial_tracker.roomdatabase.source;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAllSources extends AsyncTask<Void, Void, List<Source>> {
    private DaoSource dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAllSources(DaoSource dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Source> doInBackground(final Void... params) {
        return this.dao.getAllSources();
    }

    @Override
    protected void onPostExecute(List<Source> sources){
        if(listener != null)
            listener.onTaskCompleted(sources);
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Source> sources);
    }
}