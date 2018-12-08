package com.kapirawan.financial_tracker.helper;

import android.os.AsyncTask;

import com.kapirawan.financial_tracker.dao.DaoBudget;
import com.kapirawan.financial_tracker.entity.Budget;

public class AsyncBudgetMultipleInsert extends AsyncTask<Budget[], Void, Void> {
    private DaoBudget asyncTaskDao;

    public AsyncBudgetMultipleInsert(DaoBudget dao){
        asyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Budget[]... params){
        asyncTaskDao.insert(params[0]);
        return null;
    }
}
