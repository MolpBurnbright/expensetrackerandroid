package com.kapirawan.financial_tracker.roomdatabase;

import android.app.Application;

import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncAccountMaxId;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncDeleteAllAccounts;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncRetrieveAccount;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncRetrieveAllAccounts;
import com.kapirawan.financial_tracker.roomdatabase.account.AsyncRetrieveUserAccounts;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncDeleteAllExpenses;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncExpenseMaxId;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncRetrieveAccountExpenses;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncRetrieveAllExpenses;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncRetrieveExpense;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
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
}