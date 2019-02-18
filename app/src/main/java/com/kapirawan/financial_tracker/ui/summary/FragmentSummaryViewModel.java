package com.kapirawan.financial_tracker.ui.summary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.List;

public class FragmentSummaryViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<List<Sum>> expensesSum;
    private LiveData<List<Sum>> budgetsSum;
    private LiveData<List<Sum>> fundsSum;
    private LiveData<Account> account;

    public FragmentSummaryViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId){
        expensesSum = repo.readAccountSumExpenses(accountId, accountDatasourceId);
        budgetsSum = repo.readAccountSumBudgets(accountId, accountDatasourceId);
        fundsSum = repo.readAccountSumFunds(accountId, accountDatasourceId);
        account = repo.readAccount(accountId, accountDatasourceId);
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public LiveData<List<Sum>> getBudgetsSummary(){
        return budgetsSum;
    }

    public LiveData<List<Sum>> getExpensesSummary() {
        return expensesSum;
    }

    public LiveData<List<Sum>> getFundsSummary(){
        return fundsSum;
    }

    public LiveData<Preference> getSelectedAccount(){
        return repo.getSelectedAccount();
    }
}