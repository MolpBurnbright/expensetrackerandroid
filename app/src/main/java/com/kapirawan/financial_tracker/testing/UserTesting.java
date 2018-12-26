package com.kapirawan.financial_tracker.testing;

import android.app.Application;
import android.util.Log;

import com.kapirawan.financial_tracker.roomdatabase.user.User;
import com.kapirawan.financial_tracker.repository.AppRepository;

import java.util.Date;

public class UserTesting{
    AppRepository repository;
    private final String label = "TESTING";

    public UserTesting(AppRepository appRepository) {
        repository = appRepository;
    }

    public void test(){
        Log.i(label, "UserTesting Commencing");
        insertUser();
    }

    private void insertUser(){
        Log.i(label, "Inserting new User..");
        User user = new User(01, "User Name", new Date());
        repository.createUser(user, () -> {
            Log.i(label, "Insert completed");
            insertUser2();
        });
    }

    private void insertUser2(){
        Log.i(label, "Inserting new User 2..");
        User user = new User(02, "Another User", new Date());
        repository.createUser(user, () -> {
            Log.i(label, "Insert completed");
            retrieveUser();
        });
    }

    private void retrieveUser(){
        Log.i(label, "Retrieving User..");
        repository.readUser(01, user -> {
            Log.i(label, "User Id:" + user._id);
            Log.i(label, "User Name: " + user.userName);
            Log.i(label, "retrieve successful..");
            retrieveAllUsers();
        });
    }

    private void retrieveAllUsers(){
        repository.readAllUsers(users -> {
            for(int i = 0;i < users.size(); i++){
                Log.i(label, "Element " + i + " User Id: " + users.get(i)._id);
                Log.i(label, "Element " + i + " User Name: " + users.get(i).userName);
                Log.i(label, "retrieve successful..");
            }
            updateUsers();
        });
    }

    private void updateUsers(){
        Log.i(label, " Updating User 2..");
        User user = new User(02, "Name Update", new Date());
        repository.updateUser(user, () -> {
            Log.i(label, "Update successful");
            Log.i(label, "Retrieving users again..");
            repository.readAllUsers(users -> {
                for(int i = 0;i < users.size(); i++){
                    Log.i(label, "Element " + i + " User Id: " + users.get(i)._id);
                    Log.i(label, "Element " + i + " User Name: " + users.get(i).userName);
                    Log.i(label, "retrieve successful..");
                }
                deleteUsers();
            });
        });
    }

    private void deleteUsers(){
        Log.i(label, " Deleting All users..");
        repository.deleteAllUsers(()->{
            Log.i(label, "All users deleted..");
        });
    }
}