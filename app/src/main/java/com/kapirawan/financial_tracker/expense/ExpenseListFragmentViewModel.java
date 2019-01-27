package com.kapirawan.financial_tracker.expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

import java.util.List;

public class ExpenseListFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<List<Expense>> expenses;
    private LiveData<Account> account;
    private long accountId, accountDatasourceId;

    public ExpenseListFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId) {
        if (expenses != null) {
            return;
        }
        this.account = new MutableLiveData<>();
        setAccount(accountId, accountDatasourceId);
    }

    public LiveData<List<Expense>> getExpenses() {
        return expenses;
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public void setAccount(long accountId, long accountDatasourceId) {
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        expenses = repo.readAccountExpenseLD(accountId, accountDatasourceId);
        repo.readAccount(accountId, accountDatasourceId, account ->
            ((MutableLiveData<Account>) this.account).setValue(account)
        );
    }
}