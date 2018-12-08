package com.kapirawan.financial_tracker.helper;

import android.os.AsyncTask;

import com.kapirawan.financial_tracker.dao.DaoAccount;
import com.kapirawan.financial_tracker.entity.Account;

public class AsyncAccountInsert extends AsyncTask<Account, Void, Void> {
    private DaoAccount asyncTaskDao;

    public AsyncAccountInsert(DaoAccount dao){
        asyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Account... params){
        asyncTaskDao.insert(params[0]);
        return null;
    }

}