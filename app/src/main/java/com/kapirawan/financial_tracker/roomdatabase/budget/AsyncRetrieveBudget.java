package com.kapirawan.financial_tracker.roomdatabase.budget;

import android.os.AsyncTask;

public class AsyncRetrieveBudget extends AsyncTask<Long, Void, Budget> {
    private DaoBudget dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveBudget (DaoBudget dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Budget doInBackground(final Long... params) {
        return this.dao.getBudget(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(Budget budget){
        if(listener != null)
            listener.onTaskCompleted(budget);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Budget budget);
    }
}