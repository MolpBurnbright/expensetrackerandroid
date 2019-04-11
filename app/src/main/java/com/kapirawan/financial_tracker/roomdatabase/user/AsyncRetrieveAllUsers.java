package com.kapirawan.financial_tracker.roomdatabase.user;

import android.os.AsyncTask;

import java.util.List;

public class AsyncRetrieveAllUsers extends AsyncTask<Void, Void, List<User>> {
    private DaoUser dao;
    private OnTaskCompleted listener;

    public AsyncRetrieveAllUsers (DaoUser dao, OnTaskCompleted listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<User> doInBackground(final Void... params) {
        return this.dao.getAllUsers();
    }

    @Override
    protected void onPostExecute(List<User> users){
        if(listener != null)
            listener.onTaskCompleted(users);
        dao = null;
        listener = null;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<User> users);
    }
}