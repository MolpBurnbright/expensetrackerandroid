package com.kapirawan.financial_tracker.roomdatabase.expense;

import android.os.AsyncTask;

public class AsyncRetrieveExpense extends AsyncTask<Long, Void, Expense> {
    private DaoExpense dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveExpense (DaoExpense dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Expense doInBackground(final Long... params) {
        return this.dao.getExpense(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(Expense expense){
        if(listener != null)
            listener.onTaskCompleted(expense);
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Expense expense);
    }
}