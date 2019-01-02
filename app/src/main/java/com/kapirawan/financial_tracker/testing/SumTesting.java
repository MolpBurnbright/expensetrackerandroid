package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.model.Summary;
import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SumTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repo;
    private final String label = "TESTING";
    private Callback callback;

    public SumTesting(AppRepository appRepository) {
        repo = appRepository;
    }

    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Sum Testing Commencing ***");
        init(() -> insertExpenses());
    }

    private void init(Callback callback){
        repo.deleteAllExpenses(() ->
                repo.deleteAllBudgets(() ->
                        repo.deleteAllFunds(() ->
                                callback.callback())));
    }

    private void insertExpenses(){
        Log.i (label, "Inserting expenses..");
        Expense[] expenses = {
                new Expense(1, 0, 1, 0, new Date(),
                        159.25, "Food", "Rice", new Date()),
                new Expense(2, 0, 1, 0, new Date(),
                        257.25, "Food", "Pork", new Date()),
                new Expense(3, 0, 1, 0, new Date(),
                        3000, "Food", "Food budget for home", new Date()),
                new Expense(4, 0, 1, 0, new Date(),
                        20, "Fare", "Tric fare", new Date()),
                new Expense(5, 0, 1, 0, new Date(),
                        8, "Fare", "Jeep fare", new Date()),
                new Expense(6, 0, 1, 0, new Date(),
                        65, "Fare", "Bus fare", new Date()),
                new Expense(7, 0, 1, 0, new Date(),
                        1300, "Allowance", "Allowance for Armie", new Date()),
                new Expense(8, 0, 1, 0, new Date(),
                        1000, "Allowance", "Allowance for Nice", new Date()),
                new Expense(9, 0, 1, 0, new Date(),
                        654, "Utilities", "Electric bill", new Date()),
                new Expense(10, 0, 1, 0, new Date(),
                        435, "Utilities", "Water bill", new Date()),
                new Expense(11, 0, 1, 0, new Date(),
                        6000, "Education", "Tuition Fee", new Date())

        };
        repo.createMultipleExpenses(new ArrayList<> (Arrays.asList(expenses)), () -> {
            Log.i(label, "Expenses inserted successfully");
            insertBudgets();
        });
    }

    private void insertBudgets(){
        Log.i (label, "Inserting budgets..");
        Budget[] budgets = {
                new Budget(1, 0, 1, 0, new Date(),
                        3000, "Food", "Food Budget", new Date()),
                new Budget(2, 0, 1, 0, new Date(),
                        6000, "Food", "Another Food budget", new Date()),
                new Budget(3, 0, 1, 0, new Date(),
                        12000, "Food", "Food budget for home", new Date()),
                new Budget(4, 0, 1, 0, new Date(),
                        2000, "Fare", "Fare Budget", new Date()),
                new Budget(5, 0, 1, 0, new Date(),
                        3000, "Fare", "Another Fare Budget", new Date()),
                new Budget(6, 0, 1, 0, new Date(),
                        3000, "Allowance", "Allowance Budget", new Date()),
                new Budget(7, 0, 1, 0, new Date(),
                        5000, "Allowance", "Another Allowance Budget", new Date()),
                new Budget(8, 0, 1, 0, new Date(),
                        4000, "Utilities", "Utilities Budget", new Date()),
                new Budget(9, 0, 1, 0, new Date(),
                        3000, "Wellness", "Wellness", new Date())

        };
        repo.createMultipleBudgets(new ArrayList<> (Arrays.asList(budgets)), () -> {
            Log.i(label, "Budgets inserted successfully");
            insertFunds();
        });
    }

    private void insertFunds(){
        Log.i (label, "Inserting funds..");
        Fund[] funds = {
                new Fund(1, 0, 1, 0, new Date(),
                        12000, "Salary", "April Salary", new Date()),
                new Fund(2, 0, 1, 0, new Date(),
                        30000, "Salary", "May Salary", new Date()),
                new Fund(3, 0, 1, 0, new Date(),
                        90000, "Loan", "Loan for Home Improvement", new Date()),
                new Fund(4, 0, 1, 0, new Date(),
                        1000, "Others", "Earnings from investment", new Date()),
                new Fund(5, 0, 1, 0, new Date(),
                        3000, "Others", "Earnings from selling items", new Date()),
        };
        repo.createMultipleFunds(new ArrayList<> (Arrays.asList(funds)), () -> {
            Log.i(label, "Funds inserted successfully");
            retrieveSumExpenses();
        });
    }

    private List<Sum> sumExpenses;

    private void retrieveSumExpenses(){
        Log.i(label, "Retrieving sum of expenses with accountId 01 and accountDatasource 00..");
        repo.getSumAllExpenses(01, 00, sums -> {
            this.sumExpenses = sums;
            Log.i(label, "Sum of Expenses retrieved successfully..");
            Log.i(label, "Number of sum: " + sums.size());
            for (int i=0; i < sums.size(); i++){
                Log.i(label, "Element " + i);
                printSum(sums.get(i));
            }
            retrieveSumBudgets();
        });
    }

    private List<Sum> sumBudgets;

    private void retrieveSumBudgets(){
        Log.i(label, "Retrieving sum of Budgets with accountId 01 and accountDatasource 00..");
        repo.getSumAllBudgets(01, 00, sums -> {
            this.sumBudgets = sums;
            Log.i(label, "Sum of Budgets retrieved successfully..");
            Log.i(label, "Number of sum: " + sums.size());
            for (int i=0; i < sums.size(); i++){
                Log.i(label, "Element " + i);
                printSum(sums.get(i));
            }
            retrieveSumFunds();
        });
    }

    private void retrieveSumFunds(){
        Log.i(label, "Retrieving sum of Funds with accountId 01 and accountDatasource 00..");
        repo.getSumAllFunds(01, 00, sums -> {
            Log.i(label, "Sum of Funds retrieved successfully..");
            Log.i(label, "Number of sum: " + sums.size());
            for (int i=0; i < sums.size(); i++){
                Log.i(label, "Element " + i);
                printSum(sums.get(i));
            }
            getSummary();
            deleteAllData(() -> testCompleted());
        });
    }

    private void printSum(Sum sum){
        Log.i(label, "name: " + sum.name);
        Log.i(label, "amount: " + sum.amount);
    }

    private void getSummary(){
        Log.i(label, "Getting summary for Expense and Budget");
        List<Summary> summaries = Summary.getSummaries(sumExpenses, sumBudgets);
        Log.i(label, "Printing summary for Expense and Budget");
        for(Summary summary : summaries){
            Log.i(label, "name: " + summary.name);
            Log.i(label, "total expense: " + summary.totalExpense);
            Log.i(label, "total budget: " + summary.totalBudget);
            Log.i(label, "remaining budget: " + (summary.totalBudget - summary.totalExpense));
        }
    }

    private void deleteAllData(Callback callback){
        repo.deleteAllExpenses(() ->
                repo.deleteAllBudgets(() ->
                        repo.deleteAllFunds(() ->
                                callback.callback())));
    }

    private void testCompleted(){
        Log.i(label, "*** Sum Testing COMPLETED*** ");
        this.callback.callback();
    }
}