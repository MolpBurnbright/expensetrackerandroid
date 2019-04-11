package com.kapirawan.financial_tracker.roomdatabase.category;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAllCategories extends AsyncTask<Void, Void, List<Category>> {
    private DaoCategory dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAllCategories(DaoCategory dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Category> doInBackground(final Void... params) {
        return this.dao.getAllCategories();
    }

    @Override
    protected void onPostExecute(List<Category> categories){
        if(listener != null)
            listener.onTaskCompleted(categories);
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Category> categories);
    }
}