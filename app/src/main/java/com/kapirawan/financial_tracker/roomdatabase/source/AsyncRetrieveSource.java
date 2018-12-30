package com.kapirawan.financial_tracker.roomdatabase.source;

import android.os.AsyncTask;

public class AsyncRetrieveSource extends AsyncTask<Long, Void, Source> {
    private DaoSource dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveSource(DaoSource dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Source doInBackground(final Long... params) {
        return this.dao.getSource(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(Source source){
        if(listener != null)
            listener.onTaskCompleted(source);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Source source);
    }
}