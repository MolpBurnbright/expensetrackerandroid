package com.kapirawan.financial_tracker.database.roomdatabase.user;

import android.os.AsyncTask;

public class AsyncRetrieveUser extends AsyncTask<Long, Void, User> {
    private DaoUser dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveUser (DaoUser dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected User doInBackground(final Long... params) {
        return this.dao.getUser(params[0]);
    }

    protected void onPostExecute(User user){
        if (listener != null)
            listener.onTaskCompleted(user);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(User user);
    }
}