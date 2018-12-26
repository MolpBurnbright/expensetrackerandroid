package com.kapirawan.financial_tracker.roomdatabase.expense;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAllExpenses extends AsyncTask<Void, Void, List<Expense>> {
    private DaoExpense dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAllExpenses (DaoExpense dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Expense> doInBackground(final Void... params) {
        return this.dao.getAllExpenses();
    }

    @Override
    protected void onPostExecute(List<Expense> expenses){
        if(listener != null)
            listener.onTaskCompleted(expenses);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Expense> expenses);
    }
}