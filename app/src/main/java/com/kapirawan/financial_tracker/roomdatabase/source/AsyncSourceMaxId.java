package com.kapirawan.financial_tracker.roomdatabase.source;

import android.os.AsyncTask;

public class AsyncSourceMaxId extends AsyncTask<Long, Void, Long> {
    private DaoSource dao;
    private OnTaskCompleted listener;

    public AsyncSourceMaxId(DaoSource dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Long doInBackground(final Long... params) {
        return this.dao.getMaxId(params[0]);
    }

    @Override
    protected void onPostExecute(Long maxId){
        if(listener != null)
            listener.onTaskCompleted(maxId);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(long maxId);
    }
}