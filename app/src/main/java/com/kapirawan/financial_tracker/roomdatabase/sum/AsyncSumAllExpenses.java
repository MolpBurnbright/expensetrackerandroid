package com.kapirawan.financial_tracker.roomdatabase.sum;

import android.os.AsyncTask;

import java.util.List;

public class AsyncSumAllExpenses extends AsyncTask<Long, Void, List<Sum>> {
    private DaoSum dao;
    private OnTaskCompleted listener;

    public AsyncSumAllExpenses (DaoSum dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Sum> doInBackground(final Long... params) {
        return this.dao.getSumAllExpenses(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(List<Sum> sums){
        if(listener != null)
            listener.onTaskCompleted(sums);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Sum> sums);
    }
}