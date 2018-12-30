package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repository;
    private final String label = "TESTING";
    private Callback callback;

    public CategoryTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Category Testing Commencing ***");
        insertCategories();
    }

    private void insertCategories() {
        Log.i(label, "Inserting single category..");
        Category category = new Category(0, 0, 1, 0,
                "Food", new Date());
        repository.createCategory(category,() -> {
            Log.i(label, "One Record Insert Successful..");
            repository.createCategory(
                    new Category(0, 0, 1, 0, "Fare",
                            new Date())
                    , () -> {
                        Log.i(label, "Another Record Insert Successful..");
                        insertMultipleCategories();
                    }
            );
        });
    }

    private void insertMultipleCategories(){
        Log.i(label, "Inserting multiple categories..");
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, 01, 2, 1,
                "Utilities", new Date()));
        categories.add(new Category(2, 01, 2, 1,
                "Allowance", new Date()));
        repository.createMultipleCategories(categories, () -> {
            Log.i(label, "Multiple Records Insert Successful..");
            retrieveCategory();
        });
    }

    private void retrieveCategory() {
        Log.i(label, "Retrieving single category..");
        Log.i(label, "Retrieving category with _id 01 and datasource 01..");
        repository.readCategory(01, 1, category -> {
            Log.i(label, "Single record retrieve successful..");
            printCategory(category);
            retrieveAccountCategories();
        });
    }

    private void retrieveAccountCategories(){
        Log.i(label, "Retrieving categories for account 02 accountDatasourceId 01");
        repository.readAccountCategory(02, 1, categories -> {
            Log.i(label, "Account Categories retrieve successful..");
            Log.i(label, "Number of categories retrieved: " + categories.size());
            for (int i=0; i < categories.size(); i++){
                Log.i(label, "Element " + i);
                printCategory(categories.get(i));
            }
            retrieveAllCategories();
        });
    }

    private void retrieveAllCategories(){
        Log.i(label, "Retrieving all category");
        repository.readAllCategories(categories -> {
            Log.i(label, "All Categories retrieve successful..");
            Log.i(label, "Number of categories retrieved: " + categories.size());
            for (int i=0; i < categories.size(); i++){
                Log.i(label, "Printing Element " + i);
                printCategory(categories.get(i));
            }
            updateCategories();
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

    private void updateCategories() {
        Log.i(label, "Updating Category with _id: 01 and datasourceId: 01..");
        Category category = new Category(01, 1, 03, 3,
                "Others", new Date());
        repository.updateCategory(category, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving all categories again..");
            repository.readAllCategories(categories -> {
                Log.i(label, "Number of categories retrieved: " + categories.size());
                for (int i=0; i < categories.size(); i++){
                    Log.i(label, "Element " + i);
                    printCategory(categories.get(i));
                }
                deleteCategory();
            });
        });
    }

    private void deleteCategory() {
        Log.i(label, "Deleting category with _id 01 and datasoureId 0");
        repository.deleteCategory(new Category(01, 0, 0, 0,
                        null, null),
                () -> {
                    Log.i(label, "Category with _id: 01 and datasource: 0 has been deleted");
                    Log.i(label, "Retrieving all categories again..");
                    repository.readAllCategories(categories -> {
                        Log.i(label, "Number of categories retrieved: " + categories.size());
                        for (int i=0; i < categories.size(); i++){
                            Log.i(label, "Printing Element " + i);
                            printCategory(categories.get(i));
                        }
                        deleteAllCategories();
                    });
                });
    }

    private void deleteAllCategories(){
        Log.i(label, " Deleting All categories..");
        repository.deleteAllCategories(() -> {
            Log.i(label, "All categories deleted..");
            repository.readAllCategories(categories -> {
                Log.i(label, "Number of categories retrieved: " + categories.size());
                for (int i = 0; i < categories.size(); i++) {
                    Log.i(label, "Element " + i);
                    printCategory(categories.get(i));
                }
                testCompleted();
            });
        });
    }

    private void testCompleted(){
        Log.i(label, "*** Category Testing COMPLETED*** ");
        this.callback.callback();
    }
}