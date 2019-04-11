package com.kapirawan.financial_tracker.roomdatabase.budget;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAccountBudgets extends AsyncTask<Long, Void, List<Budget>> {
    private DaoBudget dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAccountBudgets (DaoBudget dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Budget> doInBackground(final Long... params) {
        return this.dao.getAccountBudgets(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(List<Budget> budgets){
        if(listener != null)
            listener.onTaskCompleted(budgets);
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Budget> budgets);
    }
}