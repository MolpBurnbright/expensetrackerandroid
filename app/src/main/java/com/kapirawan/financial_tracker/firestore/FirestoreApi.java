package com.kapirawan.financial_tracker.firestore;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

public class FirestoreApi {

    private FirebaseFirestore firestoreDB;
    private static FirestoreApi INSTANCE;
    private static final String TAG = "FirestoreApi";

    private static final String EXPENSES = "expenses";

    public static FirestoreApi getInstance() {
        if(INSTANCE == null) {
            synchronized (FirestoreApi.class) {
                if(INSTANCE == null) {
                    INSTANCE = new FirestoreApi();
                }
            }
        }
        return INSTANCE;
    }

    private FirestoreApi() {
        firestoreDB = FirebaseFirestore.getInstance();
    }

    public void addExpense(Expense expense){
        firestoreDB.collection("expenses")
                .add(expense).addOnSuccessListener(documentReference -> {
                    Log.i(TAG, "document added with ID:" + documentReference.getId());
                });
    }
}