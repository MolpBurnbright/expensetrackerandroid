package com.kapirawan.financial_tracker.roomdatabase.fund;

import android.os.AsyncTask;

public class AsyncDeleteAllFunds extends AsyncTask<Void, Void, Void> {
    private DaoFund asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncDeleteAllFunds(DaoFund dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.deleteAllFunds();
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
