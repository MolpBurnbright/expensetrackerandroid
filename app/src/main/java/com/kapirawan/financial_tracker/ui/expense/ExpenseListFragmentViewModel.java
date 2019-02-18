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

import java.util.List;

public class ExpenseListFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<List<Expense>> expenses;
    private LiveData<Account> account;

    public ExpenseListFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId) {
        expenses = repo.readAccountExpenseLD(accountId, accountDatasourceId);
        account = repo.readAccount(accountId, accountDatasourceId);
    }

    public LiveData<List<Expense>> getExpenses() {
        return expenses;
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public LiveData<Preference> getSelectedAccount(){
        return repo.getSelectedAccount();
    }

}