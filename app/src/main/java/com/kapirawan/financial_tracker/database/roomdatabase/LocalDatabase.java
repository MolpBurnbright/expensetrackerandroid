package com.kapirawan.financial_tracker.database.roomdatabase;

import android.app.Application;

import com.kapirawan.financial_tracker.database.roomdatabase.expense.AsyncDeleteAllExpenses;
import com.kapirawan.financial_tracker.database.roomdatabase.expense.AsyncRetrieveAccountExpenses;
import com.kapirawan.financial_tracker.database.roomdatabase.expense.AsyncRetrieveAllExpenses;
import com.kapirawan.financial_tracker.database.roomdatabase.expense.AsyncRetrieveExpense;
import com.kapirawan.financial_tracker.database.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.database.roomdatabase.user.AsyncDeleteAllUsers;
import com.kapirawan.financial_tracker.database.roomdatabase.user.AsyncRetrieveAllUsers;
import com.kapirawan.financial_tracker.database.roomdatabase.user.AsyncRetrieveUser;
import com.kapirawan.financial_tracker.database.roomdatabase.user.User;

import java.util.List;

/*
* Author - Rodney Caneda
* Email - molp.burnbright@gmail.com
* */

public class LocalDatabase {
    AppRoomDatabase db;

    public interface Callback{
        void onTaskCompleted();
    }

    public interface CallbackReturnObject <T>{
        void onTaskCompleted(T objects);
    }

    public interface CallbackReturnMultipleObjects <T>{
        void onTaskCompleted(List<T> objects);
    }

    public LocalDatabase(Application app) {
        db = AppRoomDatabase.getDatabase(app);
    }
    
    /*** CRUD for User Entity ***/

    public void createUser (User user, Callback callback){
        new AsyncInsert(db.daoUser(), () -> callback.onTaskCompleted()).execute(user);
    }

    public void readUser (long userId, CallbackReturnObject callback){
        new AsyncRetrieveUser(db.daoUser(), (User) -> callback.onTaskCompleted(User))
                .execute(userId);
    }

    public void readAllUsers(CallbackReturnMultipleObjects callback){
        new AsyncRetrieveAllUsers(db.daoUser(), (users) -> callback.onTaskCompleted(users))
                .execute();
    }

    public void updateUser(User user, Callback callback){
        new AsyncUpdate(db.daoUser(), () -> callback.onTaskCompleted()).execute(user);
    }

    public void delete(User user, Callback callback){
        new AsyncDelete(db.daoUser(), () -> callback.onTaskCompleted()).execute(user);
    }

    public void deleteAllUsers(Callback callback){
        new AsyncDeleteAllUsers(db.daoUser(), () -> callback.onTaskCompleted()).execute();
    }

    /*** CRUD for Expenses Entity ***/

    public void createExpense (Expense expense, Callback callback){
        new AsyncInsert(db.daoExpense(), () -> callback.onTaskCompleted()).execute(expense);
    }

    public void createMultipleExpenses (List<Expense> expenses, Callback callback){
        new AsyncInsertMultiple(db.daoExpense(), () -> callback.onTaskCompleted()).execute(expenses);
    }

    public void readExpense (long expenseId, CallbackReturnObject callback){
        new AsyncRetrieveExpense(db.daoExpense(), (expense) -> callback.onTaskCompleted(expense))
                .execute(expenseId);
    }

    public void readAccountExpenses (long accountId, CallbackReturnMultipleObjects callback){
        new AsyncRetrieveAccountExpenses(db.daoExpense(), (expenses) -> callback.onTaskCompleted(expenses))
                .execute(accountId);
    }

    public void readAllExpenses(CallbackReturnMultipleObjects callback){
        new AsyncRetrieveAllExpenses(db.daoExpense(), (expenses) -> callback.onTaskCompleted(expenses))
                .execute();
    }

    public void updateExpense(Expense expense, Callback callback){
        new AsyncUpdate(db.daoExpense(), () -> callback.onTaskCompleted()).execute(expense);
    }

    public void deleteExpense(Expense expense, Callback callback){
        new AsyncDelete(db.daoExpense(), () -> callback.onTaskCompleted()).execute(expense);
    }

    public void deleteAllExpenses(Callback callback){
        new AsyncDeleteAllExpenses(db.daoExpense(), () -> callback.onTaskCompleted()).execute();
    }

}