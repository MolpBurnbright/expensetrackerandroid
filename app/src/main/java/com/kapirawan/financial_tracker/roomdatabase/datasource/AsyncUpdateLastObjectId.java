package com.kapirawan.financial_tracker.roomdatabase.datasource;

import android.os.AsyncTask;

public class AsyncUpdateLastObjectId extends AsyncTask<Long, Void, Void> {
    private DaoDatasource dao;
    private OnTaskCompleted listener;

    public AsyncUpdateLastObjectId(DaoDatasource dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Long... params) {
        this.dao.updateLastId(params[0], params[1], params[2]);
        return null;
    }

    @Override
    protected void onPostExecute(Void arg) {
        if (listener != null)
            listener.onTaskCompleted();
    }

    public interface OnTaskCompleted {
        void onTaskCompleted();
    }
}