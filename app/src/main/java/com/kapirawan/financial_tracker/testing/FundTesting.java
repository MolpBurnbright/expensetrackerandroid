package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FundTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repository;
    private final String label = "TESTING";
    private Callback callback;

    public FundTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Fund Testing Commencing ***");
        insertFunds();
    }

    private void insertFunds() {
        Log.i(label, "Inserting single fund..");
        Fund fund = new Fund(0, 0, 1, 0, new Date(),
                100.25, "Salary", "Salary for December", new Date());
        repository.createFund(fund,() -> {
            Log.i(label, "One Record Insert Successful..");
            repository.createFund(
                    new Fund(0, 0, 1, 0,
                            new Date(), 100.25, "Salary", "Salary for January",
                            new Date())
                    , () -> {
                        Log.i(label, "Another Record Insert Successful..");
                        insertMultipleFunds();
                    }
            );
        });
    }

    private void insertMultipleFunds(){
        Log.i(label, "Inserting multiple funds..");
        List<Fund> funds = new ArrayList<>();
        funds.add(new Fund(1, 01, 2, 1,new Date(),
                200.50, "Loan", "Personal Load", new Date()));
        funds.add(new Fund(2, 01, 2, 1, new Date(),
                300.75, "Others", "Other Income", new Date()));
        repository.createMultipleFunds(funds, () -> {
            Log.i(label, "Multiple Records Insert Successful..");
            retrieveFund();
        });
    }

    private void retrieveFund() {
        Log.i(label, "Retrieving single fund..");
        Log.i(label, "Retrieving fund with _id 01 and datasource 01..");
        repository.readFund(01, 1, fund -> {
            Log.i(label, "Single record retrieve successful..");
            printFund(fund);
            retrieveAccountFund();
        });
    }

    private void retrieveAccountFund(){
        Log.i(label, "Retrieving funds for account 02 accountDatasourceId 01");
        repository.readAccountFunds(02, 1, funds -> {
            Log.i(label, "Account Funds retrieve successful..");
            Log.i(label, "Number of funds retrieved: " + funds.size());
            for (int i=0; i < funds.size(); i++){
                Log.i(label, "Element " + i);
                printFund(funds.get(i));
            }
            retrieveAllFunds();
        });
    }

    private void retrieveAllFunds(){
        Log.i(label, "Retrieving all fund");
        repository.readAllFunds(funds -> {
            Log.i(label, "All Funds retrieve successful..");
            Log.i(label, "Number of funds retrieved: " + funds.size());
            for (int i=0; i < funds.size(); i++){
                Log.i(label, "Printing Element " + i);
                printFund(funds.get(i));
            }
            updateFunds();
        });
    }

    private void printFund(Fund fund){
        Log.i(label, "_id: " + fund._id);
        Log.i(label, "datasourceId: " + fund.datasourceId);
        Log.i(label, "accountId: " + fund.accountId);
        Log.i(label, "accountDatasourceId: " + fund.accountDatasourceId);
        Log.i(label, "date: " + new SimpleDateFormat("yyyy-MM-dd").format(fund.date));
        Log.i(label, "amount: " + fund.amount);
        Log.i(label, "type: " + fund.type);
        Log.i(label, "details: " + fund.details);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(fund.updateTimestamp));
    }

    private void updateFunds() {
        Log.i(label, " Updating Fund with _id: 01 and datasourceId: 01..");
        Fund fund = new Fund(01, 1, 03, 3,
                new Date(), 888.88, "Others", "Other Fund", new Date());
        repository.updateFund(fund, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving all funds again..");
            repository.readAllFunds(funds -> {
                Log.i(label, "Number of funds retrieved: " + funds.size());
                for (int i=0; i < funds.size(); i++){
                    Log.i(label, "Element " + i);
                    printFund(funds.get(i));
                }
                deleteFund();
            });
        });
    }

    private void deleteFund() {
        Log.i(label, "Deleting fund with _id 01 and datasoureId 0");
        repository.deleteFund(new Fund(01, 0, 0, 0,
                        null, 0, null, null, null),
                () -> {
                    Log.i(label, "Fund with _id: 01 and datasource: 0 has been deleted");
                    Log.i(label, "Retrieving all funds again..");
                    repository.readAllFunds(funds -> {
                        Log.i(label, "Number of funds retrieved: " + funds.size());
                        for (int i=0; i < funds.size(); i++){
                            Log.i(label, "Printing Element " + i);
                            printFund(funds.get(i));
                        }
                        deleteAllFunds();
                    });
                });
    }

    private void deleteAllFunds(){
        Log.i(label, " Deleting All funds..");
        repository.deleteAllFunds(() -> {
            Log.i(label, "All funds deleted..");
            repository.readAllFunds(funds -> {
                Log.i(label, "Number of funds retrieved: " + funds.size());
                for (int i = 0; i < funds.size(); i++) {
                    Log.i(label, "Element " + i);
                    printFund(funds.get(i));
                }
                testCompleted();
            });
        });
    }

    private void testCompleted(){
        Log.i(label, "*** Fund Testing COMPLETED*** ");
        this.callback.callback();
    }
}