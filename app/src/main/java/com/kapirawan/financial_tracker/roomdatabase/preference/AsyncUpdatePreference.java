package com.kapirawan.financial_tracker.roomdatabase.preference;

import android.os.AsyncTask;

public class AsyncUpdatePreference extends AsyncTask<Void, Void, Void> {
    private DaoPreference asyncTaskDao;
    private OnTaskCompleted listener;
    private Preference pref;

    public AsyncUpdatePreference (DaoPreference dao, Preference pref, OnTaskCompleted listener) {
        asyncTaskDao = dao;
        this.listener = listener;
        this.pref = pref;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        asyncTaskDao.update(pref);
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
