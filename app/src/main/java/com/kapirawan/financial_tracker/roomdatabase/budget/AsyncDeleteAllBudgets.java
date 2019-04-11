package com.kapirawan.financial_tracker.roomdatabase.budget;

import android.os.AsyncTask;

public class AsyncDeleteAllBudgets extends AsyncTask<Void, Void, Void> {
    private DaoBudget asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncDeleteAllBudgets (DaoBudget dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.deleteAllBudgets();
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        if (listener != null)
            this.listener.onTaskCompleted();
        asyncTaskDao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted();
    }
}
