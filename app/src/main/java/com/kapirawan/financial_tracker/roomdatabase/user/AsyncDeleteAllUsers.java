package com.kapirawan.financial_tracker.roomdatabase.user;

import android.os.AsyncTask;

public class AsyncDeleteAllUsers extends AsyncTask<Void, Void, Void> {
    private DaoUser asyncTaskDao;
    private OnTaskCompleted listener;

    public AsyncDeleteAllUsers (DaoUser dao, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.deleteAllUsers();
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
