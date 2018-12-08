package com.kapirawan.financial_tracker.helper;

import android.os.AsyncTask;

import com.kapirawan.financial_tracker.dao.DaoExpense;
import com.kapirawan.financial_tracker.entity.Expense;

public class AsyncExpenseInsert extends AsyncTask<Expense, Void, Void> {
        private DaoExpense asyncTaskDao;

        public AsyncExpenseInsert(DaoExpense dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Expense... params){
            asyncTaskDao.insert(params[0]);
            return null;
        }
}