package com.kapirawan.financial_tracker.roomdatabase;

import android.app.Application;

import com.kapirawan.financial_tracker.roomdatabase.account.AsyncAccountMaxId;
import com.kapirawan.financial_tracker.roomdatabase.datasource.AsyncRetrieveUserFirstDatasource;
import com.kapirawan.financial_tracker.roomdatabase.datasource.Datasource;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncDeleteAllExpenses;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncExpenseMaxId;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncRetrieveAccountExpenses;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncRetrieveAllExpenses;
import com.kapirawan.financial_tracker.roomdatabase.expense.AsyncRetrieveExpense;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.roomdatabase.user.AsyncDeleteAllUsers;
import com.kapirawan.financial_tracker.roomdatabase.user.AsyncRetrieveAllUsers;
import com.kapirawan.financial_tracker.roomdatabase.user.AsyncRetrieveFirstUser;
import com.kapirawan.financial_tracker.roomdatabase.user.AsyncRetrieveUser;
import com.kapirawan.financial_tracker.roomdatabase.user.User;

import java.util.Date;
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

    private class MaxId {
        public long accountMaxId = 0;
        public long expenseMaxId = 0;
    }

    private enum ObjectType {
        ACCOUNT, EXPENSE
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
    private Datasource datasource;
    private MaxId maxIds;

    private LocalDatabase(Application app) {
        db = AppRoomDatabase.getDatabase(app);
        setDatasource(() -> setMaxIds());
    }

    private void setDatasource(Callback callback){
        if(datasource == null){
            getDatasource(datasource -> {
                synchronized (this.datasource){
                    if (this.datasource == null)
                        this.datasource = datasource;
                }
                if(callback != null)
                    callback.onTaskCompleted();
            });
        }
    }

    private void getDatasource(CallbackReturnObject<Datasource> callback){
        synchronized (this) {
            if (datasource == null) {
                getUser(user -> {
                    new AsyncRetrieveUserFirstDatasource(db.daoDatasource(), datasource -> {
                        if (datasource != null)
                            callback.onTaskCompleted(datasource);
                        else{
                            final Datasource newDatasource = new Datasource(0, 0,
                                    "Default Datasource", new Date());
                            new AsyncInsert<Datasource>(db.daoDatasource(),
                                    () -> callback.onTaskCompleted(newDatasource)).execute(newDatasource);
                        }
                    }).execute(user._id);
                });
            }
        }
    }

    private void getUser(CallbackReturnObject<User> callback){
        new AsyncRetrieveFirstUser(db.daoUser(), user -> {
            if (user != null)
                callback.onTaskCompleted(user);
            else{
                final User newUser = new User(0, "Default User", new Date());
                new AsyncInsert<User>(db.daoUser(), () -> callback.onTaskCompleted(newUser))
                        .execute(newUser);
            }
        }).execute();
    }

    private void setMaxIds(){
        if(this.maxIds == null){
            synchronized (this.maxIds) {
                this.maxIds = new MaxId();
                setAccountMaxId();
                setExpenseMaxId();
            }
        }
    }

    private void setAccountMaxId(){
        new AsyncAccountMaxId(db.daoAccount(), maxId -> {
            synchronized (this.maxIds) {
                this.maxIds.accountMaxId = maxId;
            }
        }).execute(this.datasource._id);
    }

    private void setExpenseMaxId(){
        new AsyncExpenseMaxId(db.daoExpense(), maxId -> {
            synchronized (this) {
                this.maxIds.expenseMaxId = maxId;
            }
        }).execute(this.datasource._id);
    }

    private long getNewId(ObjectType type){
        long id = 0;
        if(this.maxIds == null) {
            //Wait for a few milliseconds so the constructor thread can setup the maxIds..
            try {
                Thread.sleep(100);
            }catch(Exception ex){

            }
        }
        synchronized (this.maxIds) {
            switch (type) {
                case ACCOUNT:
                    this.maxIds.accountMaxId += 1;
                    id = this.maxIds.accountMaxId;
                case EXPENSE:
                    this.maxIds.expenseMaxId += 1;
                    id = maxIds.expenseMaxId;
            }
        }
        return id;
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

    /*** CRUD for Expenses Entity ***/

    public void createExpense (Expense expense, Callback callback){
        if(expense._id == 0)
            expense._id = getNewId(ObjectType.EXPENSE);
        new AsyncInsert<Expense>(db.daoExpense(), callback::onTaskCompleted).execute(expense);
    }

    public void createMultipleExpenses (List<Expense> expenses, Callback callback){
        new AsyncInsertMultiple<Expense> (db.daoExpense(),
                callback::onTaskCompleted).execute(expenses);
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