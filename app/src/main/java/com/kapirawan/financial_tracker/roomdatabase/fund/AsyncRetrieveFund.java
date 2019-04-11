package com.kapirawan.financial_tracker.roomdatabase.fund;

import android.os.AsyncTask;

public class AsyncRetrieveFund extends AsyncTask<Long, Void, Fund> {
    private DaoFund dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveFund(DaoFund dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected Fund doInBackground(final Long... params) {
        return this.dao.getFund(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(Fund fund){
        if(listener != null)
            listener.onTaskCompleted(fund);
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Fund fund);
    }
}