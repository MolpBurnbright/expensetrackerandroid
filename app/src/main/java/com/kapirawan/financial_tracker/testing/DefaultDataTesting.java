package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.datasource.Datasource;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;
import com.kapirawan.financial_tracker.roomdatabase.user.User;

import java.text.SimpleDateFormat;

public class DefaultDataTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repository;
    private final String label = "TESTING";
    private Callback callback;

    public DefaultDataTesting(AppRepository appRepository) {
        repository = appRepository;
    }
    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Default Testing Commencing ***");
        retrieveDefaultUser();
    }


    private void retrieveDefaultUser() {
        Log.i(label, "Retrieving the default user..");
        Log.i(label, "Retrieving user with _id 0..");
        repository.readUser(0, user -> {
            Log.i(label, "Single record retrieve successful..");
            printUser(user);
            retrieveDefaultDatasource();
        });
    }

    private void printUser(User user){
        Log.i(label, "_id: " + user._id);
        Log.i(label, "name: " + user.name);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(user.updateTimestamp));
    }

    private void retrieveDefaultDatasource(){
        Log.i(label, "Retrieving single datasource..");
        Log.i(label, "Retrieving datasource with _id 0..");
        repository.readDatasource(0, datasource -> {
            Log.i(label, "Single record retrieve successful..");
            printDatasource(datasource);
            retrieveDefaultAccount();
        });
    }

    private void printDatasource(Datasource datasource){
        Log.i(label, "_id: " + datasource._id);
        Log.i(label, "userId: " + datasource.userId);
        Log.i(label, "name: " + datasource.name);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(datasource.updateTimestamp));
    }

    private void retrieveDefaultAccount() {
        Log.i(label, "Retrieving single account..");
        Log.i(label, "Retrieving account with _id 01 and datasource 0..");
        repository.readAccount(01, 0, account -> {
            Log.i(label, "Single record retrieve successful..");
            printAccount(account);
            retrieveDefaultCategories();
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

    private void retrieveDefaultCategories(){
        Log.i(label, "Retrieving categories with accountId: 01 and accountDatasourceId 0");
        repository.readAccountCategories(1, 0, categories -> {
            Log.i(label, "Account Categories retrieve successful..");
            Log.i(label, "Number of categories retrieved: " + categories.size());
            for (int i=0; i < categories.size(); i++){
                Log.i(label, "Element " + i);
                printCategory(categories.get(i));
            }
            retrieveDefaultSource();
        });
    }

    private void printCategory(Category category){
        Log.i(label, "_id: " + category._id);
        Log.i(label, "datasourceId: " + category.datasourceId);
        Log.i(label, "accountId: " + category.accountId);
        Log.i(label, "accountDatasourceId: " + category.accountDatasourceId);
        Log.i(label, "name: " + category.name);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(category.updateTimestamp));
    }

    private void retrieveDefaultSource(){
        Log.i(label, "Retrieving sources with accountId: 01 accountDatasourceId 0");
        repository.readAccountSources(1, 0, sources -> {
            Log.i(label, "Account Sources retrieve successful..");
            Log.i(label, "Number of sources retrieved: " + sources.size());
            for (int i=0; i < sources.size(); i++){
                Log.i(label, "Element " + i);
                printSource(sources.get(i));
            }
            testCompleted();
        });
    }

    private void printSource(Source source){
        Log.i(label, "_id: " + source._id);
        Log.i(label, "datasourceId: " + source.datasourceId);
        Log.i(label, "accountId: " + source.accountId);
        Log.i(label, "accountDatasourceId: " + source.accountDatasourceId);
        Log.i(label, "name: " + source.name);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(source.updateTimestamp));
    }

    private void testCompleted(){
        Log.i(label, "*** Account Testing COMPLETED*** ");
        this.callback.callback();
    }
}