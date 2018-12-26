package com.kapirawan.financial_tracker.repository;

import android.app.Application;

import com.kapirawan.financial_tracker.roomdatabase.LocalDatabase;
import com.kapirawan.financial_tracker.roomdatabase.user.User;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

import java.util.List;

public class AppRepository {
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
        localDb = new LocalDatabase(app);
    }

    /*** CRUD for User ***/
    
    public void createUser (User user, Callback callback){
        localDb.createUser(user, () -> callback.onTaskCompleted());
    }

    public void readUser (long userId, CallbackReturnUser callback){
        localDb.readUser(userId, (user) -> callback.onTaskCompleted(user));
    }

    public void readAllUsers(CallbackReturnMultipleUsers callback){
        localDb.readAllUsers((users) -> callback.onTaskCompleted(users));
    }

    public void updateUser (User user, Callback callback){
        localDb.updateUser(user, () -> callback.onTaskCompleted());
    }

    public void deleteUser (User user, Callback callback){
        localDb.deleteUser(user, () -> callback.onTaskCompleted());
    }

    public void deleteAllUsers(Callback callback){
        localDb.deleteAllUsers(() -> callback.onTaskCompleted());
    }

    public interface Callback {
        void onTaskCompleted();
    }

    public interface CallbackReturnUser{
        void onTaskCompleted(User user);
    }

    public interface  CallbackReturnMultipleUsers{
        void onTaskCompleted(List<User> users);
    }

    /*** CRUD for Expense ***/

    public void createExpense (Expense expense, Callback callback){
        localDb.createExpense(expense, () -> callback.onTaskCompleted());
    }

    public void createMultipleExpense (List<Expense> expenses, Callback callback){
        localDb.createMultipleExpenses(expenses, () -> callback.onTaskCompleted());
    }

    public void readExpense (long expenseId, CallbackReturnExpense callback){
        localDb.readExpense(expenseId, (expense) -> callback.onTaskCompleted(expense));
    }

    public void readAccountExpense(long accountId, CallbackReturnMultipleExpenses callback){
        localDb.readAccountExpenses(accountId, (expenses) -> callback.onTaskCompleted(expenses));
    }

    public void readAllExpenses(CallbackReturnMultipleExpenses callback){
        localDb.readAllExpenses((expenses) -> callback.onTaskCompleted(expenses));
    }

    public void updateExpense (Expense expense, Callback callback){
        localDb.updateExpense(expense, () -> callback.onTaskCompleted());
    }

    public void deleteExpense (Expense expense, Callback callback){
        localDb.deleteExpense(expense, () -> callback.onTaskCompleted());
    }

    public void deleteAllExpenses(Callback callback){
        localDb.deleteAllExpenses(() -> callback.onTaskCompleted());
    }

    public interface CallbackReturnExpense{
        void onTaskCompleted(Expense expense);
    }

    public interface  CallbackReturnMultipleExpenses{
        void onTaskCompleted(List<Expense> expenses);
    }

}