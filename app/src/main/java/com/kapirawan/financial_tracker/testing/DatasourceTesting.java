package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.datasource.Datasource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatasourceTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repository;
    private final String label = "TESTING";
    private Callback callback;

    public DatasourceTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Datasource Testing Commencing ***");
        insertDatasources();
    }

    private void insertDatasources() {
        Log.i(label, "Inserting single datasource..");
        Datasource datasource = new Datasource(0, 0, "Default Datasource",
                new Date());
        repository.createDatasource(datasource,() -> {
            Log.i(label, "One Record Insert Successful..");
            insertMultipleDatasources();
        });
    }

    private void insertMultipleDatasources(){
        Log.i(label, "Inserting multiple datasources..");
        List<Datasource> datasources = new ArrayList<>();
        datasources.add(new Datasource(2, 1, "Another Datasource 2", new Date()));
        datasources.add(new Datasource(3, 2, "Another Datasource 3", new Date()));
        datasources.add(new Datasource(4, 3, "Another Datasource 4", new Date()));
        repository.createMultipleDatasources(datasources, () -> {
            Log.i(label, "Multiple Records Insert Successful..");
            retrieveDatasource();
        });
    }

    private void retrieveDatasource() {
        Log.i(label, "Retrieving single datasource..");
        Log.i(label, "Retrieving datasource with _id 0..");
        repository.readDatasource(0, datasource -> {
            Log.i(label, "Single record retrieve successful..");
            printDatasource(datasource);
            retrieveUserFirstDatasource();
        });
    }

    private void retrieveUserFirstDatasource(){
        Log.i(label, "Retrieving datasource with userId 2..");
        repository.readUserFirstDatasource(2, datasource -> {
            Log.i(label, "Single record retrieve successful..");
            printDatasource(datasource);
            retrieveAllDatasources();
        });
    }

    private void retrieveAllDatasources(){
        Log.i(label, "Retrieving all datasources");
        repository.readAllDatasources(datasources -> {
            Log.i(label, "All datasources retrieved successfully..");
            Log.i(label, "Number of datasources retrieved: " + datasources.size());
            for (int i=0; i < datasources.size(); i++){
                Log.i(label, "Printing Element " + i);
                printDatasource(datasources.get(i));
            }
            updateDatasources();
        });
    }

    private void printDatasource(Datasource datasource){
        Log.i(label, "_id: " + datasource._id);
        Log.i(label, "userId: " + datasource.userId);
        Log.i(label, "name: " + datasource.name);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(datasource.updateTimestamp));
    }

    private void updateDatasources() {
        Log.i(label, "Updating Datasource with _id 0..");
        Datasource datasource = new Datasource(0, 6, "Updated Datasource Name",
                new Date());
        repository.updateDatasource(datasource, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving all datasources again..");
            repository.readAllDatasources(datasources -> {
                Log.i(label, "Number of datasources retrieved: " + datasources.size());
                for (int i=0; i < datasources.size(); i++){
                    Log.i(label, "Element " + i);
                    printDatasource(datasources.get(i));
                }
                deleteDatasource();
            });
        });
    }

    private void deleteDatasource() {
        Log.i(label, "Deleting datasource with _id 0");
        repository.deleteDatasource(new Datasource(0, 0, null, null),
                () -> {
                    Log.i(label, "Datasource with _id: 0 has been deleted");
                    Log.i(label, "Retrieving all datasources again..");
                    repository.readAllDatasources(datasources -> {
                        Log.i(label, "Number of datasources retrieved: " + datasources.size());
                        for (int i=0; i < datasources.size(); i++){
                            Log.i(label, "Element " + i);
                            printDatasource(datasources.get(i));
                        }
                        deleteAllDatasources();
                    });
                });
    }

    private void deleteAllDatasources(){
        Log.i(label, " Deleting All datasources..");
        repository.deleteAllDatasources(() -> {
            Log.i(label, "All datasources deleted..");
            repository.readAllDatasources(datasources -> {
                Log.i(label, "Number of datasources retrieved: " + datasources.size());
                for (int i = 0; i < datasources.size(); i++) {
                    Log.i(label, "Element " + i);
                    printDatasource(datasources.get(i));
                }
                testCompleted();
            });
        });
    }

    private void testCompleted(){
        Log.i(label, "*** Datasource Testing COMPLETED*** ");
        this.callback.callback();
    }
}