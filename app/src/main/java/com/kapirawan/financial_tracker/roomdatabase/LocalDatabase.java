package com.kapirawan.financial_tracker.roomdatabase;

import android.app.Application;

import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncAccountMaxId;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncDeleteAllAccounts;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncRetrieveAccount;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncRetrieveAllAccounts;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncRetrieveUserAccounts;
import com.kapirawan.financial_tracker.roomdatabase.budget.AsyncBudgetMaxId;
import com.kapirawan.financial_tracker.roomdatabase.budget.AsyncDeleteAllBudgets;
import com.kapirawan.financial_tracker.roomdatabase.budget.AsyncRetrieveAccountBudgets;
import com.kapirawan.financial_tracker.roomdatabase.budget.AsyncRetrieveAllBudgets;
import com.kapirawan.financial_tracker.roomdatabase.budget.AsyncRetrieveBudget;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;
import com.kapirawan.financial_tracker.roomdatabase.datasource.AsyncDeleteAllDatasources;
import com.kapirawan.financial_tracker.roomdatabase.datasource.AsyncRetrieveAllDatasources;
import com.kapirawan.financial_tracker.roomdatabase.datasource.AsyncRetrieveDatasource;
import com.kapirawan.financial_tracker.roomdatabase.datasource.AsyncRetrieveUserFirstDatasource;
import com.kapirawan.financial_tracker.roomdatabase.datasource.Datasource;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncDeleteAllExpenses;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncExpenseMaxId;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncRetrieveAccountExpenses;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncRetrieveAllExpenses;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncRetrieveExpense;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.roomdatabase.fund.AsyncDeleteAllFunds;
import com.kapirawan.financial_tracker.roomdatabase.fund.AsyncFundMaxId;
import com.kapirawan.financial_tracker.roomdatabase.fund.AsyncRetrieveAccountFunds;
import com.kapirawan.financial_tracker.roomdatabase.fund.AsyncRetrieveAllFunds;
import com.kapirawan.financial_tracker.roomdatabase.fund.AsyncRetrieveFund;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;
import com.kapirawan.financial_tracker.roomdatabase.user.AsyncDeleteAllUsers;
import com.kapirawan.financial_tracker.roomdatabase.user.AsyncRetrieveAllUsers;
import com.kapirawan.financial_tracker.roomdatabase.user.AsyncRetrieveUser;
import com.kapirawan.financial_tracker.roomdatabase.user.User;

import java.util.List;

/*
* Author - Rodney Caneda
* Email - molp.burnbright@gmail.com
* */

public class LocalDatabase {

    public interface Callback{
        void onTaskCompleted();
    }

    public interface CallbackReturnObject <T>{
        void onTaskCompleted(T objects);
    }

    public interface CallbackReturnMultipleObjects <T>{
        void onTaskCompleted(List<T> objects);
    }

    private static LocalDatabase INSTANCE;

    public static LocalDatabase getInstance(Application app) {
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDatabase(app);
                }
            }
        }
        return INSTANCE;
    }

    private AppRoomDatabase db;

    private LocalDatabase(Application app) {
        db = AppRoomDatabase.getDatabase(app);
    }

    /*** CRUD for User Entity ***/

    public void createUser (User user, Callback callback){
        new AsyncInsert<User> (db.daoUser(), callback::onTaskCompleted).execute(user);
    }

    public void createMultipleUsers (List<User> users, Callback callback){
        User[] userArray = new User[users.size()];
        userArray = users.toArray(userArray);
        new AsyncInsertMultiple<User>(db.daoUser(), callback::onTaskCompleted).execute(userArray);
    }
    public void readUser (long userId, CallbackReturnObject<User> callback){
        new AsyncRetrieveUser(db.daoUser(), callback::onTaskCompleted).execute(userId);
    }

    public void readAllUsers(CallbackReturnMultipleObjects<User> callback){
        new AsyncRetrieveAllUsers(db.daoUser(), callback::onTaskCompleted).execute();
    }

    public void updateUser(User user, Callback callback){
        new AsyncUpdate<User> (db.daoUser(), callback::onTaskCompleted).execute(user);
    }

    public void deleteUser(User user, Callback callback){
        new AsyncDelete <User> (db.daoUser(), callback::onTaskCompleted).execute(user);
    }

    public void deleteAllUsers(Callback callback){
        new AsyncDeleteAllUsers(db.daoUser(), callback::onTaskCompleted).execute();
    }

    /*** CRUD for Datasource Entity ***/

    public void createDatasource (Datasource datasource, Callback callback){
        new AsyncInsert<Datasource> (db.daoDatasource(),
                callback::onTaskCompleted).execute(datasource);
    }

    public void createMultipleDatasources (List<Datasource> datasources, Callback callback){
        Datasource[] datasourceArray = new Datasource[datasources.size()];
        datasourceArray = datasources.toArray(datasourceArray);
        new AsyncInsertMultiple<Datasource>(db.daoDatasource(),
                callback::onTaskCompleted).execute(datasourceArray);
    }
    public void readDatasource (long datasourceId, CallbackReturnObject<Datasource> callback){
        new AsyncRetrieveDatasource(db.daoDatasource(),
                callback::onTaskCompleted).execute(datasourceId);
    }

    public void readAllDatasources(CallbackReturnMultipleObjects<Datasource> callback){
        new AsyncRetrieveAllDatasources(db.daoDatasource(), callback::onTaskCompleted).execute();
    }

    public void readUserFirstDatasource(long userId, CallbackReturnObject<Datasource> callback){
        new AsyncRetrieveUserFirstDatasource(db.daoDatasource(), callback::onTaskCompleted)
                .execute(userId);
    }

    public void updateDatasource(Datasource datasource, Callback callback){
        new AsyncUpdate<Datasource> (db.daoDatasource(),
                callback::onTaskCompleted).execute(datasource);
    }

    public void deleteDatasource(Datasource datasource, Callback callback){
        new AsyncDelete <Datasource> (db.daoDatasource(),
                callback::onTaskCompleted).execute(datasource);
    }

    public void deleteAllDatasources(Callback callback){
        new AsyncDeleteAllDatasources(db.daoDatasource(), callback::onTaskCompleted).execute();
    }
    
    /*** CRUD for Account Entity ***/

    public void createAccount (Account account, Callback callback) {
        if (account._id == 0) {
            new AsyncAccountMaxId(db.daoAccount(), maxId -> {
                account._id = maxId + 1;
                new AsyncInsert<Account>(db.daoAccount(), callback::onTaskCompleted)
                        .execute(account);
            }).execute(account.datasourceId);
        } else
            new AsyncInsert<Account>(db.daoAccount(), callback::onTaskCompleted).execute(account);
    }

    public void createMultipleAccounts (List<Account> accounts, Callback callback){
        Account[] accountArray = new Account[accounts.size()];
        accountArray = accounts.toArray(accountArray);
        new AsyncInsertMultiple<Account> (db.daoAccount(),
                callback::onTaskCompleted).execute(accountArray);
    }

    public void readAccount (long accountId, long datasourceId,
                             CallbackReturnObject<Account> callback){
        new AsyncRetrieveAccount(db.daoAccount(), callback::onTaskCompleted)
                .execute(accountId, datasourceId);
    }

    public void readUserAccounts (long userId, CallbackReturnObject<List<Account>> callback){
        new AsyncRetrieveUserAccounts(db.daoAccount(), callback::onTaskCompleted).execute(userId);

    }

    public void readAllAccounts(CallbackReturnMultipleObjects<Account> callback){
        new AsyncRetrieveAllAccounts(db.daoAccount(), callback::onTaskCompleted).execute();
    }

    public void updateAccount(Account account, Callback callback){
        new AsyncUpdate<Account> (db.daoAccount(), callback::onTaskCompleted).execute(account);
    }

    public void deleteAccount(Account account, Callback callback){
        new AsyncDelete<Account> (db.daoAccount(), callback::onTaskCompleted).execute(account);
    }

    public void deleteAllAccounts(Callback callback){
        new AsyncDeleteAllAccounts(db.daoAccount(), callback::onTaskCompleted).execute();
    }

    /*** CRUD for Expenses Entity ***/

    public void createExpense (Expense expense, Callback callback) {
        if (expense._id == 0) {
            new AsyncExpenseMaxId(db.daoExpense(), maxId -> {
                expense._id = maxId + 1;
                new AsyncInsert<Expense>(db.daoExpense(), callback::onTaskCompleted)
                        .execute(expense);
            }).execute(expense.datasourceId);
        } else
            new AsyncInsert<Expense>(db.daoExpense(), callback::onTaskCompleted).execute(expense);
    }

    public void createMultipleExpenses (List<Expense> expenses, Callback callback){
        Expense[] expenseArray = new Expense[expenses.size()];
        expenseArray = expenses.toArray(expenseArray);
        new AsyncInsertMultiple<Expense> (db.daoExpense(),
                callback::onTaskCompleted).execute(expenseArray);
    }

    public void readExpense (long expenseId, long datasourceId,
                             CallbackReturnObject<Expense> callback){
        new AsyncRetrieveExpense(db.daoExpense(), callback::onTaskCompleted)
                .execute(expenseId, datasourceId);
    }

    public void readAccountExpenses (long accountId, long accountDatasourceId,
                                     CallbackReturnMultipleObjects<Expense> callback){
        new AsyncRetrieveAccountExpenses(db.daoExpense(), callback::onTaskCompleted)
                .execute(accountId, accountDatasourceId);
    }

    public void readAllExpenses(CallbackReturnMultipleObjects<Expense> callback){
        new AsyncRetrieveAllExpenses(db.daoExpense(), callback::onTaskCompleted).execute();
    }

    public void updateExpense(Expense expense, Callback callback){
        new AsyncUpdate<Expense> (db.daoExpense(), callback::onTaskCompleted).execute(expense);
    }

    public void deleteExpense(Expense expense, Callback callback){
        new AsyncDelete<Expense> (db.daoExpense(), callback::onTaskCompleted).execute(expense);
    }

    public void deleteAllExpenses(Callback callback){
        new AsyncDeleteAllExpenses(db.daoExpense(), callback::onTaskCompleted).execute();
    }

    /*** CRUD for Budgets Entity ***/

    public void createBudget (Budget budget, Callback callback) {
        if (budget._id == 0) {
            new AsyncBudgetMaxId(db.daoBudget(), maxId -> {
                budget._id = maxId + 1;
                new AsyncInsert<Budget>(db.daoBudget(), callback::onTaskCompleted)
                        .execute(budget);
            }).execute(budget.datasourceId);
        } else
            new AsyncInsert<Budget>(db.daoBudget(), callback::onTaskCompleted).execute(budget);
    }

    public void createMultipleBudgets (List<Budget> budgets, Callback callback){
        Budget[] budgetArray = new Budget[budgets.size()];
        budgetArray = budgets.toArray(budgetArray);
        new AsyncInsertMultiple<Budget> (db.daoBudget(),
                callback::onTaskCompleted).execute(budgetArray);
    }

    public void readBudget (long budgetId, long datasourceId,
                             CallbackReturnObject<Budget> callback){
        new AsyncRetrieveBudget(db.daoBudget(), callback::onTaskCompleted)
                .execute(budgetId, datasourceId);
    }

    public void readAccountBudgets (long accountId, long accountDatasourceId,
                                     CallbackReturnMultipleObjects<Budget> callback){
        new AsyncRetrieveAccountBudgets(db.daoBudget(), callback::onTaskCompleted)
                .execute(accountId, accountDatasourceId);
    }

    public void readAllBudgets(CallbackReturnMultipleObjects<Budget> callback){
        new AsyncRetrieveAllBudgets(db.daoBudget(), callback::onTaskCompleted).execute();
    }

    public void updateBudget(Budget budget, Callback callback){
        new AsyncUpdate<Budget> (db.daoBudget(), callback::onTaskCompleted).execute(budget);
    }

    public void deleteBudget(Budget budget, Callback callback){
        new AsyncDelete<Budget> (db.daoBudget(), callback::onTaskCompleted).execute(budget);
    }

    public void deleteAllBudgets(Callback callback){
        new AsyncDeleteAllBudgets(db.daoBudget(), callback::onTaskCompleted).execute();
    }
    
    /*** CRUD for Funds Entity ***/

    public void createFund (Fund fund, Callback callback) {
        if (fund._id == 0) {
            new AsyncFundMaxId(db.daoFund(), maxId -> {
                fund._id = maxId + 1;
                new AsyncInsert<Fund>(db.daoFund(), callback::onTaskCompleted)
                        .execute(fund);
            }).execute(fund.datasourceId);
        } else
            new AsyncInsert<Fund>(db.daoFund(), callback::onTaskCompleted).execute(fund);
    }

    public void createMultipleFunds (List<Fund> funds, Callback callback){
        Fund[] fundArray = new Fund[funds.size()];
        fundArray = funds.toArray(fundArray);
        new AsyncInsertMultiple<Fund> (db.daoFund(),
                callback::onTaskCompleted).execute(fundArray);
    }

    public void readFund (long fundId, long datasourceId,
                            CallbackReturnObject<Fund> callback){
        new AsyncRetrieveFund(db.daoFund(), callback::onTaskCompleted)
                .execute(fundId, datasourceId);
    }

    public void readAccountFunds (long accountId, long accountDatasourceId,
                                    CallbackReturnMultipleObjects<Fund> callback){
        new AsyncRetrieveAccountFunds(db.daoFund(), callback::onTaskCompleted)
                .execute(accountId, accountDatasourceId);
    }

    public void readAllFunds(CallbackReturnMultipleObjects<Fund> callback){
        new AsyncRetrieveAllFunds(db.daoFund(), callback::onTaskCompleted).execute();
    }

    public void updateFund(Fund fund, Callback callback){
        new AsyncUpdate<Fund> (db.daoFund(), callback::onTaskCompleted).execute(fund);
    }

    public void deleteFund(Fund fund, Callback callback){
        new AsyncDelete<Fund> (db.daoFund(), callback::onTaskCompleted).execute(fund);
    }

    public void deleteAllFunds(Callback callback){
        new AsyncDeleteAllFunds(db.daoFund(), callback::onTaskCompleted).execute();
    }
}