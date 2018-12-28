package com.kapirawan.financial_tracker.repository;

import android.app.Application;

import com.kapirawan.financial_tracker.roomdatabase.LocalDatabase;
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

    /*** CRUD for Expense ***/

    public void createExpense (Expense expense, Callback callback){
        localDb.createExpense(expense, callback::onTaskCompleted);
    }

    public void createMultipleExpense (List<Expense> expenses, Callback callback){
        localDb.createMultipleExpenses(expenses, callback::onTaskCompleted);
    }

    public void readExpense (long expenseId, CallbackReturnObject<Expense> callback){
        localDb.readExpense(expenseId, callback::onTaskCompleted);
    }

    public void readAccountExpense(long accountId, CallbackReturnMultipleObjects<Expense> callback){
        localDb.readAccountExpenses(accountId, callback::onTaskCompleted);
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
}