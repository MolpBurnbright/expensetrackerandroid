package com.kapirawan.financial_tracker.testing;

import android.app.Application;
import android.util.Log;

import com.kapirawan.financial_tracker.roomdatabase.user.User;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserTesting {

    public interface Callback {
        void callback();
    }

    AppRepository repository;
    private final String label = "TESTING";
    private Callback callback;

    public UserTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test(Callback callback) {
        this.callback = callback;
        Log.i(label, "*** User Testing Commencing ***");
        insertUsers();
    }

    private void insertUsers() {
        Log.i(label, "Inserting single user..");
        User user = new User(0, "Default User", new Date());
        repository.createUser(user,() -> {
            Log.i(label, "One Record Insert Successful..");
            insertMultipleUsers();
        });
    }

    private void insertMultipleUsers(){
        Log.i(label, "Inserting multiple users..");
        List<User> users = new ArrayList<>();
        users.add(new User(3, "Another User 2", new Date()));
        users.add(new User(4, "Another User 3", new Date()));
        users.add(new User(5, "Another User 4", new Date()));
        repository.createMultipleUsers(users, () -> {
            Log.i(label, "Multiple Records Insert Successful..");
            retrieveUser();
        });
    }

    private void retrieveUser() {
        Log.i(label, "Retrieving single user..");
        Log.i(label, "Retrieving user with _id 0..");
        repository.readUser(0, user -> {
            Log.i(label, "Single record retrieve successful..");
            printUser(user);
            retrieveAllUsers();
        });
    }

    private void retrieveAllUsers(){
        Log.i(label, "Retrieving all users");
        repository.readAllUsers(users -> {
            Log.i(label, "All users retrieved successfully..");
            Log.i(label, "Number of users retrieved: " + users.size());
            for (int i=0; i < users.size(); i++){
                Log.i(label, "Printing Element " + i);
                printUser(users.get(i));
            }
            updateUsers();
        });
    }

    private void printUser(User user){
        Log.i(label, "_id: " + user._id);
        Log.i(label, "name: " + user.name);
        Log.i(label, "updateDate: " +
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(user.updateTimestamp));
    }

    private void updateUsers() {
        Log.i(label, "Updating User with _id 0..");
        User user = new User(0, "Updated User Name", new Date());
        repository.updateUser(user, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving all users again..");
            repository.readAllUsers(users -> {
                Log.i(label, "Number of users retrieved: " + users.size());
                for (int i=0; i < users.size(); i++){
                    Log.i(label, "Element " + i);
                    printUser(users.get(i));
                }
                deleteUser();
            });
        });
    }

    private void deleteUser() {
        Log.i(label, "Deleting user with _id 0");
        repository.deleteUser(new User(0, null, null),
                () -> {
                    Log.i(label, "User with _id: 0 has been deleted");
                    Log.i(label, "Retrieving all users again..");
                    repository.readAllUsers(users -> {
                        Log.i(label, "Number of users retrieved: " + users.size());
                        for (int i=0; i < users.size(); i++){
                            Log.i(label, "Element " + i);
                            printUser(users.get(i));
                        }
                        deleteAllUsers();
                    });
                });
    }

    private void deleteAllUsers(){
        Log.i(label, " Deleting All users..");
        repository.deleteAllUsers(() -> {
            Log.i(label, "All users deleted..");
            repository.readAllUsers(users -> {
                Log.i(label, "Number of users retrieved: " + users.size());
                for (int i = 0; i < users.size(); i++) {
                    Log.i(label, "Element " + i);
                    printUser(users.get(i));
                }
                testCompleted();
            });
        });
    }

    private void testCompleted(){
        Log.i(label, "*** User Testing COMPLETED*** ");
        this.callback.callback();
    }
}