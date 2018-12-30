package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.roomdatabase.source.Source;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SourceTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repository;
    private final String label = "TESTING";
    private Callback callback;

    public SourceTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Source Testing Commencing ***");
        insertSources();
    }

    private void insertSources() {
        Log.i(label, "Inserting single source..");
        Source source = new Source(0, 0, 1, 0, "Salary",
                new Date());
        repository.createSource(source,() -> {
            Log.i(label, "One Record Insert Successful..");
            repository.createSource(
                    new Source(0, 0, 1, 0, "Loan",
                            new Date())
                    , () -> {
                        Log.i(label, "Another Record Insert Successful..");
                        insertMultipleSources();
                    }
            );
        });
    }

    private void insertMultipleSources(){
        Log.i(label, "Inserting multiple sources..");
        List<Source> sources = new ArrayList<>();
        sources.add(new Source(1, 01, 2, 1, "Donation",
                new Date()));
        sources.add(new Source(2, 01, 2, 1, "Others",
                new Date()));
        repository.createMultipleSources(sources, () -> {
            Log.i(label, "Multiple Records Insert Successful..");
            retrieveSource();
        });
    }

    private void retrieveSource() {
        Log.i(label, "Retrieving single source..");
        Log.i(label, "Retrieving source with _id 01 and datasource 01..");
        repository.readSource(01, 1, source -> {
            Log.i(label, "Single record retrieve successful..");
            printSource(source);
            retrieveAccountSource();
        });
    }

    private void retrieveAccountSource(){
        Log.i(label, "Retrieving sources for account 02 accountDatasourceId 01");
        repository.readAccountSource(02, 1, sources -> {
            Log.i(label, "Account Sources retrieve successful..");
            Log.i(label, "Number of sources retrieved: " + sources.size());
            for (int i=0; i < sources.size(); i++){
                Log.i(label, "Element " + i);
                printSource(sources.get(i));
            }
            retrieveAllSources();
        });
    }

    private void retrieveAllSources(){
        Log.i(label, "Retrieving all source");
        repository.readAllSources(sources -> {
            Log.i(label, "All Sources retrieve successful..");
            Log.i(label, "Number of sources retrieved: " + sources.size());
            for (int i=0; i < sources.size(); i++){
                Log.i(label, "Printing Element " + i);
                printSource(sources.get(i));
            }
            updateSources();
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

    private void updateSources() {
        Log.i(label, " Updating Source with _id: 01 and datasourceId: 01..");
        Source source = new Source(01, 1, 03, 3,
                "Some other sources", new Date());
        repository.updateSource(source, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving all sources again..");
            repository.readAllSources(sources -> {
                Log.i(label, "Number of sources retrieved: " + sources.size());
                for (int i=0; i < sources.size(); i++){
                    Log.i(label, "Element " + i);
                    printSource(sources.get(i));
                }
                deleteSource();
            });
        });
    }

    private void deleteSource() {
        Log.i(label, "Deleting source with _id 01 and datasoureId 0");
        repository.deleteSource(new Source(01, 0, 0, 0,
                        null, null),
                () -> {
                    Log.i(label, "Source with _id: 01 and datasource: 0 has been deleted");
                    Log.i(label, "Retrieving all sources again..");
                    repository.readAllSources(sources -> {
                        Log.i(label, "Number of sources retrieved: " + sources.size());
                        for (int i=0; i < sources.size(); i++){
                            Log.i(label, "Printing Element " + i);
                            printSource(sources.get(i));
                        }
                        deleteAllSources();
                    });
                });
    }

    private void deleteAllSources(){
        Log.i(label, " Deleting All sources..");
        repository.deleteAllSources(() -> {
            Log.i(label, "All sources deleted..");
            repository.readAllSources(sources -> {
                Log.i(label, "Number of sources retrieved: " + sources.size());
                for (int i = 0; i < sources.size(); i++) {
                    Log.i(label, "Element " + i);
                    printSource(sources.get(i));
                }
                testCompleted();
            });
        });
    }

    private void testCompleted(){
        Log.i(label, "*** Source Testing COMPLETED*** ");
        this.callback.callback();
    }
}