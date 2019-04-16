package com.kapirawan.financial_tracker.ui.summary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.List;

public class FragmentSummaryViewModel extends AndroidViewModel {
    private AppRepository repo;
    private long userId;
    private LiveData<List<Sum>> expensesSum;
    private LiveData<List<Sum>> budgetsSum;
    private LiveData<List<Sum>> fundsSum;
    private LiveData<Account> account;
    private LiveData<Double> totalExpense, totalBudget, totalFund;
    private LiveData<Preference> selectedAccount;

    public FragmentSummaryViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId){
        expensesSum = repo.readAccountSumExpenses(accountId, accountDatasourceId);
        budgetsSum = repo.readAccountSumBudgets(accountId, accountDatasourceId);
        fundsSum = repo.readAccountSumFunds(accountId, accountDatasourceId);
        account = repo.readAccount(accountId, accountDatasourceId);
        totalExpense = repo.readTotalExpense(accountId, accountDatasourceId);
        totalBudget = repo.readTotalBudget(accountId, accountDatasourceId);
        totalFund = repo.readTotalFund(accountId, accountDatasourceId);
    }

    public void initUserId(long userId){
        this.userId = userId;
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
        if(selectedAccount == null)
            selectedAccount = repo.getSelectedAccount(userId);
        return selectedAccount;
    }

    public LiveData<Double> getTotalBudget(){
        return totalBudget;
    }

    public LiveData<Double> getTotalExpense(){
        return totalExpense;
    }

    public LiveData<Double> getTotalFund(){
        return totalFund;
    }

}