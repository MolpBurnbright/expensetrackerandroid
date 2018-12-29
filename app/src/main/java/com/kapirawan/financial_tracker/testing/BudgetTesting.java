package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudgetTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repository;
    private final String label = "TESTING";
    private Callback callback;

    public BudgetTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Budget Testing Commencing ***");
        insertBudgets();
    }

    private void insertBudgets() {
        Log.i(label, "Inserting single budget..");
        Budget budget = new Budget(0, 0, 1, 0, new Date(),
                100.25, "Food", "Budget for Bigas", new Date());
        repository.createBudget(budget,() -> {
            Log.i(label, "One Record Insert Successful..");
            repository.createBudget(
                    new Budget(0, 0, 1, 0,
                            new Date(), 100.25, "Food", "Another budget for bigas",
                            new Date())
                    , () -> {
                        Log.i(label, "Another Record Insert Successful..");
                        insertMultipleBudgets();
                    }
            );
        });
    }

    private void insertMultipleBudgets(){
        Log.i(label, "Inserting multiple budgets..");
        List<Budget> budgets = new ArrayList<>();
        budgets.add(new Budget(1, 01, 2, 1,new Date(),
                200.50, "Fare", "Budget for Fare", new Date()));
        budgets.add(new Budget(2, 01, 2, 1, new Date(),
                300.75, "Food", "Budget for Food", new Date()));
        budgets.add(new Budget(3, 01, 2, 1, new Date(),
                400.25, "Allowance", "Budget for Allowance", new Date()));
        budgets.add(new Budget(4, 01, 2, 1, new Date(),
                521.75, "Utilities", "Budget for Utilities", new Date()));
        budgets.add(new Budget(5, 01, 2, 1, new Date(),
                674.55, "Food", "Another Budget for Food", new Date()));
        budgets.add(new Budget(6, 01, 2, 1, new Date(),
                730.65, "Food", "And another budget for food", new Date()));
        repository.createMultipleBudgets(budgets, () -> {
            Log.i(label, "Multiple Records Insert Successful..");
            retrieveBudget();
        });
    }

    private void retrieveBudget() {
        Log.i(label, "Retrieving single budget..");
        Log.i(label, "Retrieving budget with _id 01 and datasource 01..");
        repository.readBudget(01, 1, budget -> {
            Log.i(label, "Single record retrieve successful..");
            printBudget(budget);
            retrieveAccountBudget();
        });
    }

    private void retrieveAccountBudget(){
        Log.i(label, "Retrieving budgets for account 02 accountDatasourceId 01");
        repository.readAccountBudget(02, 1, budgets -> {
            Log.i(label, "Account Budgets retrieve successful..");
            Log.i(label, "Number of budgets retrieved: " + budgets.size());
            for (int i=0; i < budgets.size(); i++){
                Log.i(label, "Element " + i);
                printBudget(budgets.get(i));
            }
            retrieveAllBudgets();
        });
    }

    private void retrieveAllBudgets(){
        Log.i(label, "Retrieving all budget");
        repository.readAllBudgets(budgets -> {
            Log.i(label, "All Budgets retrieve successful..");
            Log.i(label, "Number of budgets retrieved: " + budgets.size());
            for (int i=0; i < budgets.size(); i++){
                Log.i(label, "Printing Element " + i);
                printBudget(budgets.get(i));
            }
            updateBudgets();
        });
    }

    private void printBudget(Budget budget){
        Log.i(label, "_id: " + budget._id);
        Log.i(label, "datasourceId: " + budget.datasourceId);
        Log.i(label, "accountId: " + budget.accountId);
        Log.i(label, "accountDatasourceId: " + budget.accountDatasourceId);
        Log.i(label, "date: " + new SimpleDateFormat("yyyy-MM-dd").format(budget.date));
        Log.i(label, "amount: " + budget.amount);
        Log.i(label, "type: " + budget.type);
        Log.i(label, "details: " + budget.details);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(budget.updateTimestamp));
    }

    private void updateBudgets() {
        Log.i(label, " Updating Budget with _id: 01 and datasourceId: 01..");
        Budget budget = new Budget(01, 1, 03, 3,
                new Date(), 888.88, "Others", "Other Budget", new Date());
        repository.updateBudget(budget, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving all budgets again..");
            repository.readAllBudgets(budgets -> {
                Log.i(label, "Number of budgets retrieved: " + budgets.size());
                for (int i=0; i < budgets.size(); i++){
                    Log.i(label, "Element " + i);
                    printBudget(budgets.get(i));
                }
                deleteBudget();
            });
        });
    }

    private void deleteBudget() {
        Log.i(label, "Deleting budget with _id 01 and datasoureId 0");
        repository.deleteBudget(new Budget(01, 0, 0, 0,
                        null, 0, null, null, null),
                () -> {
                    Log.i(label, "Budget with _id: 01 and datasource: 0 has been deleted");
                    Log.i(label, "Retrieving all budgets again..");
                    repository.readAllBudgets(budgets -> {
                        Log.i(label, "Number of budgets retrieved: " + budgets.size());
                        for (int i=0; i < budgets.size(); i++){
                            Log.i(label, "Printing Element " + i);
                            printBudget(budgets.get(i));
                        }
                        deleteAllBudgets();
                    });
                });
    }

    private void deleteAllBudgets(){
        Log.i(label, " Deleting All budgets..");
        repository.deleteAllBudgets(() -> {
            Log.i(label, "All budgets deleted..");
            repository.readAllBudgets(budgets -> {
                Log.i(label, "Number of budgets retrieved: " + budgets.size());
                for (int i = 0; i < budgets.size(); i++) {
                    Log.i(label, "Element " + i);
                    printBudget(budgets.get(i));
                }
                testCompleted();
            });
        });
    }

    private void testCompleted(){
        Log.i(label, "*** Budget Testing COMPLETED*** ");
        this.callback.callback();
    }
}