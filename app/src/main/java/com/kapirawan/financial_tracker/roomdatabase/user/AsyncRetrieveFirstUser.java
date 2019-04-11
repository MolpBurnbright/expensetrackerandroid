package com.kapirawan.financial_tracker.roomdatabase.user;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveFirstUser extends AsyncTask<Void, Void, User> {
    private DaoUser dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveFirstUser (DaoUser dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected User doInBackground(final Void... params) {
        return this.dao.getFirstUser();
    }

    @Override
    protected void onPostExecute(User user){
        if(listener != null)
            listener.onTaskCompleted(user);
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(User user);
    }
}