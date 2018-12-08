package com.kapirawan.financial_tracker.helper;

import android.os.AsyncTask;

import com.kapirawan.financial_tracker.dao.DaoSource;
import com.kapirawan.financial_tracker.entity.Source;

public class AsyncSourceMultipleInsert extends AsyncTask<Source[], Void, Void> {
    private DaoSource asyncTaskDao;

    public AsyncSourceMultipleInsert(DaoSource dao) {
        asyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Source[]... params) {
        asyncTaskDao.insert(params[0]);
        return null;
    }
}
