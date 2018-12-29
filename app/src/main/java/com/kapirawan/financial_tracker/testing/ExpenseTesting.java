package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repository;
    private final String label = "TESTING";
    private Callback callback;

    public ExpenseTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Expense Testing Commencing ***");
        insertExpenses();
    }

    private void insertExpenses() {
        Log.i(label, "Inserting single expense..");
        Expense expense = new Expense(0, 0, 1, 0, new Date(),
                100.25, "Food", "Rice Bigas", new Date());
        repository.createExpense(expense,() -> {
            Log.i(label, "One Record Insert Successful..");
            repository.createExpense(
                    new Expense(0, 0, 1, 0,
                            new Date(), 100.25, "Food", "Palay bago ang bigas",
                            new Date())
                    , () -> {
                        Log.i(label, "Another Record Insert Successful..");
                        insertMultipleExpenses();
                    }
            );
        });
    }

    private void insertMultipleExpenses(){
        Log.i(label, "Inserting multiple expenses..");
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1, 01, 2, 1,new Date(),
                200.50, "Fare", "Bus fare", new Date()));
        expenses.add(new Expense(2, 01, 2, 1, new Date(),
                300.75, "Food", "Lunch out", new Date()));
        expenses.add(new Expense(3, 01, 2, 1, new Date(),
                400.25, "Allowance", "Allowance for Nice", new Date()));
        expenses.add(new Expense(4, 01, 2, 1, new Date(),
                521.75, "Utilities", "Electric Bill", new Date()));
        expenses.add(new Expense(5, 01, 2, 1, new Date(),
                674.55, "Food", "Dinner", new Date()));
        expenses.add(new Expense(6, 01, 2, 1, new Date(),
                730.65, "Food", "Beef", new Date()));
        repository.createMultipleExpenses(expenses, () -> {
            Log.i(label, "Multiple Records Insert Successful..");
            retrieveExpense();
        });
    }

    private void retrieveExpense() {
        Log.i(label, "Retrieving single expense..");
        Log.i(label, "Retrieving expense with _id 01 and datasource 01..");
        repository.readExpense(01, 1, expense -> {
            Log.i(label, "Single record retrieve successful..");
            printExpense(expense);
            retrieveAccountExpense();
        });
    }

    private void retrieveAccountExpense(){
        Log.i(label, "Retrieving expenses for account 02 accountDatasourceId 01");
        repository.readAccountExpense(02, 1, expenses -> {
            Log.i(label, "Account Expenses retrieve successful..");
            Log.i(label, "Number of expenses retrieved: " + expenses.size());
            for (int i=0; i < expenses.size(); i++){
                Log.i(label, "Element " + i);
                printExpense(expenses.get(i));
            }
            retrieveAllExpenses();
        });
    }

    private void retrieveAllExpenses(){
        Log.i(label, "Retrieving all expense");
        repository.readAllExpenses(expenses -> {
            Log.i(label, "All Expenses retrieve successful..");
            Log.i(label, "Number of expenses retrieved: " + expenses.size());
            for (int i=0; i < expenses.size(); i++){
                Log.i(label, "Printing Element " + i);
                printExpense(expenses.get(i));
            }
            updateExpenses();
        });
    }

    private void printExpense(Expense expense){
        Log.i(label, "_id: " + expense._id);
        Log.i(label, "datasourceId: " + expense.datasourceId);
        Log.i(label, "accountId: " + expense.accountId);
        Log.i(label, "accountDatasourceId: " + expense.accountDatasourceId);
        Log.i(label, "date: " + new SimpleDateFormat("yyyy-MM-dd").format(expense.date));
        Log.i(label, "amount: " + expense.amount);
        Log.i(label, "type: " + expense.type);
        Log.i(label, "details: " + expense.details);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(expense.updateTimestamp));
    }

    private void updateExpenses() {
        Log.i(label, " Updating Expense with _id 01 and datasourceId 01..");
        Expense expense = new Expense(01, 1, 03, 3,
                new Date(), 888.88, "Others", "Other Expense", new Date());
        repository.updateExpense(expense, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving all expenses again..");
            repository.readAllExpenses(expenses -> {
                Log.i(label, "Number of expenses retrieved: " + expenses.size());
                for (int i=0; i < expenses.size(); i++){
                    Log.i(label, "Element " + i);
                    printExpense(expenses.get(i));
                }
                deleteExpense();
            });
        });
    }

    private void deleteExpense() {
        Log.i(label, "Deleting expense with _id 01 and datasoureId 0");
        repository.deleteExpense(new Expense(01, 0, 0, 0,
                        null, 0, null, null, null),
                () -> {
                    Log.i(label, "Expense with _id: 01 and datasource: 0 has been deleted");
                    Log.i(label, "Retrieving all expenses again..");
                    repository.readAllExpenses(expenses -> {
                        Log.i(label, "Number of expenses retrieved: " + expenses.size());
                        for (int i=0; i < expenses.size(); i++){
                            Log.i(label, "Printing Element " + i);
                            printExpense(expenses.get(i));
                        }
                        deleteAllExpenses();
                    });
                });
    }

    private void deleteAllExpenses(){
        Log.i(label, " Deleting All expenses..");
        repository.deleteAllExpenses(() -> {
            Log.i(label, "All expenses deleted..");
            repository.readAllExpenses(expenses -> {
                Log.i(label, "Number of expenses retrieved: " + expenses.size());
                for (int i = 0; i < expenses.size(); i++) {
                    Log.i(label, "Element " + i);
                    printExpense(expenses.get(i));
                }
                testCompleted();
            });
        });
    }

    private void testCompleted(){
        Log.i(label, "*** Expense Testing COMPLETED*** ");
        this.callback.callback();
    }
}