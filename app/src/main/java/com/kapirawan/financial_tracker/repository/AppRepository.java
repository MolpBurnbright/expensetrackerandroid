package com.kapirawan.financial_tracker.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.kapirawan.financial_tracker.dao.DaoAccount;
import com.kapirawan.financial_tracker.database.AppRoomDatabase;
import com.kapirawan.financial_tracker.entity.Account;

import java.util.List;

public class AppRepository {
    private DaoAccount daoAccount;
    private LiveData<List<Account>> accounts;

    public AppRepository(Application app) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(app);
        daoAccount = db.daoAccount();
        accounts = daoAccount.getAllAccounts();
    }

    public LiveData<List<Account>> getAccounts(){
        return accounts;
    }

    public void insert (Account account){
        new insertSingleAccountAsync(daoAccount).execute(account);
    }

    public void insert (Account[] accounts){
        new insertMultipleAccountAsync(daoAccount).execute(accounts);
    }

    private static class insertSingleAccountAsync extends AsyncTask<Account, Void, Void> {
        private DaoAccount asyncTaskDao;

        insertSingleAccountAsync(DaoAccount dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Account... params){
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertMultipleAccountAsync extends AsyncTask<Account[], Void, Void> {
        private DaoAccount asyncTaskDao;

        insertMultipleAccountAsync(DaoAccount dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Account[]... params){
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}