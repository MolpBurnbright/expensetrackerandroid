package com.kapirawan.financial_tracker.testing;

import android.app.Application;
import android.util.Log;

import com.kapirawan.financial_tracker.database.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseTesting {
    AppRepository repository;
    private final String label = "TESTING";

    public ExpenseTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test() {
        Log.i(label, "ExpenseTesting Commencing");
        insertExpenses();
    }

    private void insertExpenses() {
        Log.i(label, "Inserting new Expenses..");
        Expense expense = new Expense(01, 01, 01, new Date(), 100.25,
                "Food", "Rice Bigas", new Date());
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(0, 01, 01, new Date(), 200.50,
                "Fare", "Bus fare", new Date()));
        expenses.add(new Expense(0, 01, 01, new Date(), 300.75,
                "Food", "Lunch out", new Date()));
        expenses.add(new Expense(0, 01, 02, new Date(), 400.25,
                "Allowance", "Allowance for Nice", new Date()));
        expenses.add(new Expense(0, 01, 02, new Date(), 521.75,
                "Utilities", "Electric Bill", new Date()));
        expenses.add(new Expense(0, 01, 02, new Date(), 674.55,
                "Food", "Dinner", new Date()));
        expenses.add(new Expense(0, 01, 02, new Date(), 730.65,
                "Food", "Beef", new Date()));
        repository.createExpense(expense,() -> Log.i(label, "One Record Insert Successful.."));
        repository.createMultipleExpense(expenses, () -> {
            Log.i(label, "Multiple Records Insert Successful..");
            retrieveExpense();
        });
    }

    private void retrieveExpense() {
        Log.i(label, "Retrieving Expenses..");
        repository.readExpense(01, expense -> {
            Log.i(label, "Single record retrieve successful..");
            printExpense(expense);
        });
        Log.i(label, "Retrieving expenses for account 02");
        repository.readAccountExpense(02, expenses -> {
            Log.i(label, "Account Expenses retrieve successful..");
            Log.i(label, "Number of expenses retrieved: " + expenses.size());
            for (int i=0; i < expenses.size(); i++){
                Log.i(label, "Element " + i);
                printExpense(expenses.get(i));
            }
        });
        Log.i(label, "Retrieving all expense");
        repository.readAllExpenses(expenses -> {
            Log.i(label, "All Expenses retrieve successful..");
            Log.i(label, "Number of expenses retrieved: " + expenses.size());
            for (int i=0; i < expenses.size(); i++){
                Log.i(label, "Element " + i);
                printExpense(expenses.get(i));
            }
            updateExpenses();
        });
    }

    private void printExpense(Expense expense){
        Log.i(label, "_id: " + expense._id);
        Log.i(label, "datasourceId: " + expense.datasourceId);
        Log.i(label, "accountId: " + expense.accountId);
        Log.i(label, "date: " + new SimpleDateFormat("yyyy-MM-dd").format(expense.date));
        Log.i(label, "amount: " + expense.amount);
        Log.i(label, "type: " + expense.type);
        Log.i(label, "details: " + expense.details);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(expense.date));
    }

    private void updateExpenses() {
        Log.i(label, " Updating Expense 01..");
        Expense expense = new Expense(01, 03, 03, new Date(), 888.88,
                "Others", "Other Expense", new Date());
        repository.updateExpense(expense, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving all expenses again..");
            repository.readAllExpenses(expenses -> {
                Log.i(label, "Number of expenses retrieved: " + expenses.size());
                for (int i=0; i < expenses.size(); i++){
                    Log.i(label, "Element " + i);
                    printExpense(expenses.get(i));
                }
                deleteExpenses();
            });
        });
    }

    private void deleteExpenses() {
        Log.i(label, " Deleting All expenses..");
        repository.deleteAllExpenses(() -> {
            Log.i(label, "All expenses deleted..");
        });
    }
}