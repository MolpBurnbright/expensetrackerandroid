package com.kapirawan.financial_tracker.roomdatabase.category;

import android.os.AsyncTask;
import java.util.List;

public class AsyncRetrieveAccountCategories extends AsyncTask<Long, Void, List<Category>> {
    private DaoCategory dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAccountCategories(DaoCategory dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Category> doInBackground(final Long... params) {
        return this.dao.getAccountCategories(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(List<Category> categories){
        if(listener != null)
            listener.onTaskCompleted(categories);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Category> categories);
    }
}