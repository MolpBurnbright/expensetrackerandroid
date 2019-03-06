package com.kapirawan.financial_tracker.ui.expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;

import java.util.Date;
import java.util.List;

public class ExpenseListFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<List<Expense>> expenses;
    private LiveData<Account> account;
    private LiveData<List<String>> categories;
    private long accountId, accountDatasourceId;
    private Date startDate, endDate;

    public ExpenseListFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId) {
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        expenses = repo.readAccountExpenseLD(accountId, accountDatasourceId);
        account = repo.readAccount(accountId, accountDatasourceId);
        categories = repo.readExpenseCategories(accountId, accountDatasourceId);
        startDate = null;
        endDate = null;

    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public Date getEndDate(){
        return endDate;
    }

    public LiveData<List<Expense>> getExpenses() {
        return expenses;
    }

    public LiveData<List<String>> getExpenseCategories(){
        return categories;
    }

    public LiveData<Preference> getSelectedAccount(){
        return repo.getSelectedAccount();
    }

    public Date getStartDate(){
        return startDate;
    }

    public void setEndDate(Date date){
        endDate = date;
    }

    public void setExpenseList() {
        expenses = repo.readAccountExpenseLD(accountId, accountDatasourceId);
    }

    public void setExpenseList(String type) {
        expenses = repo.readAccountExpenses(accountId, accountDatasourceId, type);
    }

    public void setStartDate(Date date){
        startDate = date;
    }

}