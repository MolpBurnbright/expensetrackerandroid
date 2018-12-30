package com.kapirawan.financial_tracker.roomdatabase.category;

import android.os.AsyncTask;

public class AsyncRetrieveCategory extends AsyncTask<Long, Void, Category> {
    private DaoCategory dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveCategory(DaoCategory dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Category doInBackground(final Long... params) {
        return this.dao.getCategory(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(Category category){
        if(listener != null)
            listener.onTaskCompleted(category);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Category category);
    }
}