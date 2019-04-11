package com.kapirawan.financial_tracker.roomdatabase.account;

import android.os.AsyncTask;

public class AsyncAccountMaxId extends AsyncTask<Long, Void, Long> {
    private DaoAccount dao;
    private OnTaskCompleted listener;

    public AsyncAccountMaxId (DaoAccount dao, OnTaskCompleted listener) {
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
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(long maxId);
    }
}