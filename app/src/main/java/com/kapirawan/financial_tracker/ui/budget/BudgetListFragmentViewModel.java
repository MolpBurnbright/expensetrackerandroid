package com.kapirawan.financial_tracker.ui.budget;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;

import java.util.List;

public class BudgetListFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<List<Budget>> budgets;
    private LiveData<Account> account;

    public BudgetListFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId) {
        account = repo.readAccount(accountId, accountDatasourceId);
        budgets = repo.readAccountBudgets(accountId, accountDatasourceId);
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public LiveData<List<Budget>> getBudgets() {
        return budgets;
    }

    public LiveData<Preference> getSelectedAccount(){
        return repo.getSelectedAccount();
    }


}