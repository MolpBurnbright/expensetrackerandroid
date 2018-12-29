package com.kapirawan.financial_tracker.roomdatabase;

import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class AsyncInsertMultiple <T> extends AsyncTask<T[], Void, Void> {
    private DaoBase asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncInsertMultiple (DaoBase dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final T[]... params) {
        asyncTaskDao.insertMultiple(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        if (listener != null)
            this.listener.onTaskCompleted();
    }

    public interface OnTaskCompleted {
        void onTaskCompleted();
    }
}