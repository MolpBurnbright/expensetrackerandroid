package com.kapirawan.financial_tracker.database.roomdatabase;

import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class AsyncInsertMultiple <T> extends AsyncTask<List<T>, Void, Void> {
    private DaoBase asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncInsertMultiple (DaoBase dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final List<T>... params) {
        T[] objects = (T[]) new Object[params[0].size()];
        objects = params[0].toArray(objects);
        asyncTaskDao.insert(objects);
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