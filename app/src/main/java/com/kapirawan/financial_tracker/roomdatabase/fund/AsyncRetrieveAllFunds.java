package com.kapirawan.financial_tracker.roomdatabase.fund;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAllFunds extends AsyncTask<Void, Void, List<Fund>> {
    private DaoFund dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAllFunds(DaoFund dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Fund> doInBackground(final Void... params) {
        return this.dao.getAllFunds();
    }

    @Override
    protected void onPostExecute(List<Fund> funds){
        if(listener != null)
            listener.onTaskCompleted(funds);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Fund> funds);
    }
}