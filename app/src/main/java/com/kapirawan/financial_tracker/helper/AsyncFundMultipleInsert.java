package com.kapirawan.financial_tracker.helper;

import android.os.AsyncTask;

import com.kapirawan.financial_tracker.dao.DaoFund;
import com.kapirawan.financial_tracker.entity.Fund;

public class AsyncFundMultipleInsert extends AsyncTask<Fund[], Void, Void> {
    private DaoFund asyncTaskDao;

    public AsyncFundMultipleInsert(DaoFund dao) {
        asyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Fund[]... params) {
        asyncTaskDao.insert(params[0]);
        return null;
    }
}
