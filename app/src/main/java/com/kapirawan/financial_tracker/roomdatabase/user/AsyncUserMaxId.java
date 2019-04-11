package com.kapirawan.financial_tracker.roomdatabase.user;

import android.os.AsyncTask;

public class AsyncUserMaxId extends AsyncTask<Void, Void, Long> {
    private DaoUser dao;
    private OnTaskCompleted listener;

    public AsyncUserMaxId (DaoUser dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Long doInBackground(final Void... params) {
        return this.dao.getMaxId();
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