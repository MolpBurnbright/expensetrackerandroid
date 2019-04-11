package com.kapirawan.financial_tracker.roomdatabase.expense;

import android.os.AsyncTask;

public class AsyncDeleteAllExpenses extends AsyncTask<Void, Void, Void> {
    private DaoExpense asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncDeleteAllExpenses (DaoExpense dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.deleteAllExpenses();
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
