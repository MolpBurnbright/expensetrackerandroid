package com.kapirawan.financial_tracker.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.kapirawan.financial_tracker.roomdatabase.LocalDatabase;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.datasource.Datasource;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;
import com.kapirawan.financial_tracker.roomdatabase.user.User;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

import java.util.List;

public class AppRepository {

    public interface Callback {
        void onTaskCompleted();
    }

    public interface CallbackReturnObject <T>{
        void onTaskCompleted(T object);
    }

    public interface  CallbackReturnMultipleObjects <T>{
        void onTaskCompleted(List<T> users);
    }

    private LocalDatabase localDb;

    private static AppRepository INSTANCE;

    public static AppRepository getInstance(Application app) {
        if (INSTANCE == null) {
            synchronized (AppRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppRepository(app);
                }
            }
        }
        return INSTANCE;
    }

    private AppRepository(Application app) {
        localDb = LocalDatabase.getInstance(app);
    }

    /*** CRUD for User ***/
    
    public void createUser (User user, Callback callback){
        localDb.createUser(user, callback::onTaskCompleted);
    }

    public void createMultipleUsers(List<User> users, Callback callback){
        localDb.createMultipleUsers(users, callback::onTaskCompleted);
    }

    public void readUser (long userId, CallbackReturnObject<User> callback){
        localDb.readUser(userId, callback::onTaskCompleted);
    }

    public void readAllUsers(CallbackReturnMultipleObjects<User> callback){
        localDb.readAllUsers(callback::onTaskCompleted);
    }

    public void updateUser (User user, Callback callback){
        localDb.updateUser(user, callback::onTaskCompleted);
    }

    public void deleteUser (User user, Callback callback){
        localDb.deleteUser(user, callback::onTaskCompleted);
    }

    public void deleteAllUsers(Callback callback){
        localDb.deleteAllUsers(callback::onTaskCompleted);
    }
    
    /*** CRUD for Datasource ***/

    public void createDatasource (Datasource datasource, Callback callback){
        localDb.createDatasource(datasource, callback::onTaskCompleted);
    }

    public void createMultipleDatasources (List<Datasource> datasources, Callback callback){
        localDb.createMultipleDatasources(datasources, callback::onTaskCompleted);
    }

    public void readDatasource (long datasourceId, CallbackReturnObject<Datasource> callback){
        localDb.readDatasource(datasourceId, callback::onTaskCompleted);
    }

    public void readUserFirstDatasource (long userId, CallbackReturnObject<Datasource> callback){
        localDb.readUserFirstDatasource(userId, callback::onTaskCompleted);
    }

    public void readAllDatasources(CallbackReturnMultipleObjects<Datasource> callback){
        localDb.readAllDatasources(callback::onTaskCompleted);
    }

    public void updateDatasource (Datasource datasource, Callback callback){
        localDb.updateDatasource(datasource, callback::onTaskCompleted);
    }

    public void deleteDatasource (Datasource datasource, Callback callback){
        localDb.deleteDatasource(datasource, callback::onTaskCompleted);
    }

    public void deleteAllDatasources(Callback callback){
        localDb.deleteAllDatasources(callback::onTaskCompleted);
    }
    
    /*** CRUD for Account ***/

    public void createAccount (Account account, Callback callback){
        localDb.createAccount(account, callback::onTaskCompleted);
    }

    public void createMultipleAccounts (List<Account> accounts, Callback callback){
        localDb.createMultipleAccounts(accounts, callback::onTaskCompleted);
    }

    public void readAccount (long accountId, long datasourceId, 
                             CallbackReturnObject<Account> callback){
        localDb.readAccount(accountId, datasourceId, callback::onTaskCompleted);
    }

    public void readUserAccounts (long userId, CallbackReturnObject<List<Account>> callback){
        localDb.readUserAccounts(userId, callback::onTaskCompleted);
    }

    public void readAllAccounts(CallbackReturnMultipleObjects<Account> callback){
        localDb.readAllAccounts(callback::onTaskCompleted);
    }

    public void updateAccount (Account account, Callback callback){
        localDb.updateAccount(account, callback::onTaskCompleted);
    }

    public void deleteAccount (Account account, Callback callback){
        localDb.deleteAccount(account, callback::onTaskCompleted);
    }

    public void deleteAllAccounts(Callback callback){
        localDb.deleteAllAccounts(callback::onTaskCompleted);
    }

    /*** CRUD for Expense ***/

    public void createExpense (Expense expense, Callback callback){
        localDb.createExpense(expense, callback::onTaskCompleted);
    }

    public void createMultipleExpenses (List<Expense> expenses, Callback callback){
        localDb.createMultipleExpenses(expenses, callback::onTaskCompleted);
    }

    public LiveData<List<String>> getDetails(long accountId, long accountDatasourceId){
        return localDb.getDetails(accountId, accountDatasourceId);
    }

    public void readExpense (long expenseId, long datasourceId, 
                             CallbackReturnObject<Expense> callback){
        localDb.readExpense(expenseId, datasourceId, callback::onTaskCompleted);
    }

    public void readAccountExpense(long accountId, long accountDatasourceId,
                                   CallbackReturnMultipleObjects<Expense> callback){
        localDb.readAccountExpenses(accountId, accountDatasourceId, callback::onTaskCompleted);
    }

    public void readAllExpenses(CallbackReturnMultipleObjects<Expense> callback){
        localDb.readAllExpenses(callback::onTaskCompleted);
    }

    public void updateExpense (Expense expense, Callback callback){
        localDb.updateExpense(expense, callback::onTaskCompleted);
    }

    public void deleteExpense (Expense expense, Callback callback){
        localDb.deleteExpense(expense, callback::onTaskCompleted);
    }

    public void deleteAllExpenses(Callback callback){
        localDb.deleteAllExpenses(callback::onTaskCompleted);
    }

    /*** CRUD for Budget ***/

    public void createBudget (Budget budget, Callback callback){
        localDb.createBudget(budget, callback::onTaskCompleted);
    }

    public void createMultipleBudgets (List<Budget> budgets, Callback callback){
        localDb.createMultipleBudgets(budgets, callback::onTaskCompleted);
    }

    public void readBudget (long budgetId, long datasourceId,
                             CallbackReturnObject<Budget> callback){
        localDb.readBudget(budgetId, datasourceId, callback::onTaskCompleted);
    }

    public void readAccountBudget(long accountId, long accountDatasourceId,
                                   CallbackReturnMultipleObjects<Budget> callback){
        localDb.readAccountBudgets(accountId, accountDatasourceId, callback::onTaskCompleted);
    }

    public void readAllBudgets(CallbackReturnMultipleObjects<Budget> callback){
        localDb.readAllBudgets(callback::onTaskCompleted);
    }

    public void updateBudget (Budget budget, Callback callback){
        localDb.updateBudget(budget, callback::onTaskCompleted);
    }

    public void deleteBudget (Budget budget, Callback callback){
        localDb.deleteBudget(budget, callback::onTaskCompleted);
    }

    public void deleteAllBudgets(Callback callback){
        localDb.deleteAllBudgets(callback::onTaskCompleted);
    }
    
    /*** CRUD for Fund ***/

    public void createFund (Fund fund, Callback callback){
        localDb.createFund(fund, callback::onTaskCompleted);
    }

    public void createMultipleFunds (List<Fund> funds, Callback callback){
        localDb.createMultipleFunds(funds, callback::onTaskCompleted);
    }

    public void readFund (long fundId, long datasourceId,
                            CallbackReturnObject<Fund> callback){
        localDb.readFund(fundId, datasourceId, callback::onTaskCompleted);
    }

    public void readAccountFund(long accountId, long accountDatasourceId,
                                  CallbackReturnMultipleObjects<Fund> callback){
        localDb.readAccountFunds(accountId, accountDatasourceId, callback::onTaskCompleted);
    }

    public void readAllFunds(CallbackReturnMultipleObjects<Fund> callback){
        localDb.readAllFunds(callback::onTaskCompleted);
    }

    public void updateFund (Fund fund, Callback callback){
        localDb.updateFund(fund, callback::onTaskCompleted);
    }

    public void deleteFund (Fund fund, Callback callback){
        localDb.deleteFund(fund, callback::onTaskCompleted);
    }

    public void deleteAllFunds(Callback callback){
        localDb.deleteAllFunds(callback::onTaskCompleted);
    }

    /*** CRUD for Category ***/

    public void createCategory (Category category, Callback callback){
        localDb.createCategory(category, callback::onTaskCompleted);
    }

    public void createMultipleCategories (List<Category> categories, Callback callback){
        localDb.createMultipleCategories(categories, callback::onTaskCompleted);
    }

    public void readCategory (long categoryId, long datasourceId,
                          CallbackReturnObject<Category> callback){
        localDb.readCategory(categoryId, datasourceId, callback::onTaskCompleted);
    }

    public void readAccountCategory(long accountId, long accountDatasourceId,
                                CallbackReturnMultipleObjects<Category> callback){
        localDb.readAccountCategories(accountId, accountDatasourceId, callback::onTaskCompleted);
    }

    public void readAllCategories(CallbackReturnMultipleObjects<Category> callback){
        localDb.readAllCategories(callback::onTaskCompleted);
    }

    public void updateCategory (Category category, Callback callback){
        localDb.updateCategory(category, callback::onTaskCompleted);
    }

    public void deleteCategory (Category category, Callback callback){
        localDb.deleteCategory(category, callback::onTaskCompleted);
    }

    public void deleteAllCategories(Callback callback){
        localDb.deleteAllCategories(callback::onTaskCompleted);
    }
    
    /*** CRUD for Source ***/

    public void createSource (Source source, Callback callback){
        localDb.createSource(source, callback::onTaskCompleted);
    }

    public void createMultipleSources (List<Source> sources, Callback callback){
        localDb.createMultipleSources(sources, callback::onTaskCompleted);
    }

    public void readSource (long sourceId, long datasourceId,
                          CallbackReturnObject<Source> callback){
        localDb.readSource(sourceId, datasourceId, callback::onTaskCompleted);
    }

    public void readAccountSource(long accountId, long accountDatasourceId,
                                CallbackReturnMultipleObjects<Source> callback){
        localDb.readAccountSources(accountId, accountDatasourceId, callback::onTaskCompleted);
    }

    public void readAllSources(CallbackReturnMultipleObjects<Source> callback){
        localDb.readAllSources(callback::onTaskCompleted);
    }

    public void updateSource (Source source, Callback callback){
        localDb.updateSource(source, callback::onTaskCompleted);
    }

    public void deleteSource (Source source, Callback callback){
        localDb.deleteSource(source, callback::onTaskCompleted);
    }

    public void deleteAllSources(Callback callback){
        localDb.deleteAllSources(callback::onTaskCompleted);
    }

    /*** Interfaces to get sum of Entities ***/

    public void getSumAllExpenses(long accountId, long accountDatasourceId,
                                  CallbackReturnMultipleObjects<Sum> callback) {
        localDb.getSumAllExpenses(accountId, accountDatasourceId, callback::onTaskCompleted);
    }

    public void getSumAllBudgets(long accountId, long accountDatasourceId,
                                 CallbackReturnMultipleObjects<Sum> callback) {
        localDb.getSumAllBudgets(accountId, accountDatasourceId, callback::onTaskCompleted);
    }

    public void getSumAllFunds(long accountId, long accountDatasourceId,
                               CallbackReturnMultipleObjects<Sum> callback) {
        localDb.getSumAllFunds(accountId, accountDatasourceId, callback::onTaskCompleted);
    }
}