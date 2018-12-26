package com.kapirawan.financial_tracker.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.kapirawan.financial_tracker.database.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.entity.Budget;
import com.kapirawan.financial_tracker.database.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.entity.Fund;
import com.kapirawan.financial_tracker.model.ExpenseBudgetSummary;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.util.List;

public class ViewModelMainSummary extends AndroidViewModel{
    AppRepository repository;
    LiveData<List<Account>> accounts;
    LiveData<List<ExpenseBudgetSummary>> summary;
    LiveData<List<Expense>> expenses;
    LiveData<List<Budget>> budgets;
    LiveData<List<Fund>> funds;

    public ViewModelMainSummary(Application app){
        super(app);
        repository = new AppRepository(app);
//        accounts = repository.getAccounts();
    }

    public LiveData<List<ExpenseBudgetSummary>> getAccountSummary(long acountID){
        return summary;
    }

    public void setAccount(long accountID){
//        expenses = repository.getExpenses(accountID);
//        budgets = repository.getBudgets(accountID);
//        funds = repository.getFunds(accountID);

        final MediatorLiveData dataMerger = new MediatorLiveData<List<ExpenseBudgetSummary>>();

        dataMerger.addSource(expenses, expenseList -> {

            dataMerger.setValue(expenseList);
        });
    }

/*    private void constructSetSelectedMonthYear(){
        String currDateFormatter = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date());
        selectedMonth = Integer.getInteger(currDateFormatter.substring(5, 7));
        selectedYear = Integer.getInteger(currDateFormatter.substring(0, 4));
    }

    private void constructGetAccounts(){
        accounts = repository.getAccounts().observe(app, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable List<Account> accounts) {

            }
        });
        viewModelAccount.getAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable List<Account> accounts) {
                adapter.setAccounts(accounts);
            }
        });
        accounts.observe();
    }

    private void constructGetExpenses(){
        expenses = repository.bgetExpenses(accounts.getValue().get(0)._id);
    }

    public LiveData<List<Account>> getAccounts(){
        return accounts;
    }

    public void insert(Account account) {
        accountRepository.insert(account);
    }

    public void insert(Account[] accounts){
        accountRepository.insert(accounts);
    }
*/
}
