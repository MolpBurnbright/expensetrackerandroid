package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repository;
    private final String label = "TESTING";
    private Callback callback;

    public AccountTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Account Testing Commencing ***");
        insertAccounts();
    }

    private void insertAccounts() {
        Log.i(label, "Inserting single account..");
        Account account = new Account(0, 0, 0, "My Account", new Date());
        repository.createAccount(account,() -> {
            Log.i(label, "One Record Insert Successful..");
            repository.createAccount(
                    new Account(0, 0, 0, "Another Account", new Date())
                    , () -> {
                        Log.i(label, "Another Record Insert Successful..");
                        insertMultipleAccounts();
                    }
            );
        });
    }

    private void insertMultipleAccounts(){
        Log.i(label, "Inserting multiple accounts..");
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 01, 2, "Account 3", new Date()));
        accounts.add(new Account(2, 01, 2, "Account 4", new Date()));
        accounts.add(new Account(3, 01, 2, "Account 5", new Date()));
        repository.createMultipleAccount(accounts, () -> {
            Log.i(label, "Multiple Records Insert Successful..");
            retrieveAccount();
        });
    }

    private void retrieveAccount() {
        Log.i(label, "Retrieving single account..");
        Log.i(label, "Retrieving account with _id 01 and datasource 01..");
        repository.readAccount(01, 1, account -> {
            Log.i(label, "Single record retrieve successful..");
            printAccount(account);
            retrieveUserAccount();
        });
    }

    private void retrieveUserAccount(){
        Log.i(label, "Retrieving accounts for userId 02");
        repository.readUserAccounts(02,accounts -> {
            Log.i(label, "Account Accounts retrieve successful..");
            Log.i(label, "Number of accounts retrieved: " + accounts.size());
            for (int i=0; i < accounts.size(); i++){
                Log.i(label, "Element " + i);
                printAccount(accounts.get(i));
            }
            retrieveAllAccounts();
        });
    }

    private void retrieveAllAccounts(){
        Log.i(label, "Retrieving all accounts");
        repository.readAllAccounts(accounts -> {
            Log.i(label, "All accounts retrieved successfully..");
            Log.i(label, "Number of accounts retrieved: " + accounts.size());
            for (int i=0; i < accounts.size(); i++){
                Log.i(label, "Printing Element " + i);
                printAccount(accounts.get(i));
            }
            updateAccounts();
        });
    }

    private void printAccount(Account account){
        Log.i(label, "_id: " + account._id);
        Log.i(label, "datasourceId: " + account.datasourceId);
        Log.i(label, "userId: " + account.userId);
        Log.i(label, "name: " + account.name);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(account.updateTimestamp));
    }

    private void updateAccounts() {
        Log.i(label, "Updating Account 01..");
        Account account = new Account(01, 1, 03, "Updated Account Name",
                new Date());
        repository.updateAccount(account, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving all accounts again..");
            repository.readAllAccounts(accounts -> {
                Log.i(label, "Number of accounts retrieved: " + accounts.size());
                for (int i=0; i < accounts.size(); i++){
                    Log.i(label, "Element " + i);
                    printAccount(accounts.get(i));
                }
                deleteAccount();
            });
        });
    }

    private void deleteAccount() {
        Log.i(label, "Deleting account with _id 01 and datasoureId 0");
        repository.deleteAccount(new Account(01, 0, 0, null,
                        null),
                () -> {
                    Log.i(label, "Account with _id: 01 and datasource: 0 has been deleted");
                    Log.i(label, "Retrieving all accounts again..");
                    repository.readAllAccounts(accounts -> {
                        Log.i(label, "Number of accounts retrieved: " + accounts.size());
                        for (int i=0; i < accounts.size(); i++){
                            Log.i(label, "Element " + i);
                            printAccount(accounts.get(i));
                        }
                        deleteAllAccounts();
                    });
                });
    }

    private void deleteAllAccounts(){
        Log.i(label, " Deleting All accounts..");
        repository.deleteAllAccounts(() -> {
            Log.i(label, "All accounts deleted..");
            repository.readAllAccounts(accounts -> {
                Log.i(label, "Number of accounts retrieved: " + accounts.size());
                for (int i = 0; i < accounts.size(); i++) {
                    Log.i(label, "Element " + i);
                    printAccount(accounts.get(i));
                }
                testCompleted();
            });
        });
    }

    private void testCompleted(){
        Log.i(label, "*** Account Testing COMPLETED*** ");
        this.callback.callback();
    }
}