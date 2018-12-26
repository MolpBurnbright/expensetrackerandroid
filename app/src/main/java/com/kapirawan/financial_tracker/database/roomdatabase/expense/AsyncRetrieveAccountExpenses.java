package com.kapirawan.financial_tracker.database.roomdatabase.expense;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAccountExpenses extends AsyncTask<Long, Void, List<Expense>> {
    private DaoExpense dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAccountExpenses (DaoExpense dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Expense> doInBackground(final Long... params) {
        return this.dao.getAccountExpenses(params[0]);
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