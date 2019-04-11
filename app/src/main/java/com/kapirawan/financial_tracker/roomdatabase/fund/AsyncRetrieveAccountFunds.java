package com.kapirawan.financial_tracker.roomdatabase.fund;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAccountFunds extends AsyncTask<Long, Void, List<Fund>> {
    private DaoFund dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAccountFunds(DaoFund dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Fund> doInBackground(final Long... params) {
        return this.dao.getAccountFunds(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(List<Fund> funds){
        if(listener != null)
            listener.onTaskCompleted(funds);
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Fund> funds);
    }
}