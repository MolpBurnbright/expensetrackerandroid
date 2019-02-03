package com.kapirawan.financial_tracker.ui.budget;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;

import java.util.List;

public class BudgetListFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<List<Budget>> budgets;
    private LiveData<Account> account;
    private long accountId, accountDatasourceId;

    public BudgetListFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId) {
        if (budgets != null) {
            return;
        }
        this.account = new MutableLiveData<>();
        setAccount(accountId, accountDatasourceId);
    }

    public LiveData<List<Budget>> getBudgets() {
        return budgets;
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public void setAccount(long accountId, long accountDatasourceId) {
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        budgets = repo.readAccountBudgetsLD(accountId, accountDatasourceId);
        repo.readAccount(accountId, accountDatasourceId, account ->
                ((MutableLiveData<Account>) this.account).setValue(account)
        );
    }
}