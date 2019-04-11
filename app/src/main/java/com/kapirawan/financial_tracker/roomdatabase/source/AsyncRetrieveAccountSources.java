package com.kapirawan.financial_tracker.roomdatabase.source;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAccountSources extends AsyncTask<Long, Void, List<Source>> {
    private DaoSource dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAccountSources(DaoSource dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Source> doInBackground(final Long... params) {
        return this.dao.getAccountSources(params[0], params[1]);
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