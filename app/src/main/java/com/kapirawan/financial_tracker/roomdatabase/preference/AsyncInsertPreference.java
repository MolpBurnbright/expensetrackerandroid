package com.kapirawan.financial_tracker.roomdatabase.preference;

import android.os.AsyncTask;

public class AsyncInsertPreference extends AsyncTask<Void, Void, Void> {
    private DaoPreference asyncTaskDao;
    private OnTaskCompleted listener;
    private Preference pref;

    public AsyncInsertPreference(DaoPreference dao, Preference pref, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.pref = pref;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.insert(this.pref);
        return null;
    }

    @Override
    protected void onPostExecute(Void v){
        if (listener != null)
            this.listener.onTaskCompleted();
        asyncTaskDao = null;
        listener = null;
        pref = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted();
    }
}