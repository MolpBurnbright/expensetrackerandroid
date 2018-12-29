package com.kapirawan.financial_tracker.roomdatabase.budget;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAllBudgets extends AsyncTask<Void, Void, List<Budget>> {
    private DaoBudget dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAllBudgets (DaoBudget dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Budget> doInBackground(final Void... params) {
        return this.dao.getAllBudgets();
    }

    @Override
    protected void onPostExecute(List<Budget> budgets){
        if(listener != null)
            listener.onTaskCompleted(budgets);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Budget> budgets);
    }
}