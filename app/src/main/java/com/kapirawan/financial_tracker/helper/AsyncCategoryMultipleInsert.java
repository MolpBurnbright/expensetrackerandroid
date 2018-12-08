package com.kapirawan.financial_tracker.helper;

import android.os.AsyncTask;

import com.kapirawan.financial_tracker.dao.DaoCategory;
import com.kapirawan.financial_tracker.entity.Category;

public class AsyncCategoryMultipleInsert extends AsyncTask<Category[], Void, Void> {
    private DaoCategory asyncTaskDao;

    public AsyncCategoryMultipleInsert(DaoCategory dao) {
        asyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Category[]... params) {
        asyncTaskDao.insert(params[0]);
        return null;
    }
}