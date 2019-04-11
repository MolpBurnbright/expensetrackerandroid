package com.kapirawan.financial_tracker.activities;

import android.arch.persistence.room.EmptyResultSetException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.datasource.Datasource;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;
import com.kapirawan.financial_tracker.roomdatabase.user.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActivitySignIn extends AppCompatActivity {

    private static final String TAG = "ActivitySignIn";
    private static final int RC_SIGN_IN = 34254;
    private static final String DATASOURCES = "datasources";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoreDb;
    private AppRepository appRepository;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().setTitle("Expense Tracker");
        findViewById(R.id.sign_in_button).setOnClickListener(view -> {
            googleSignIn();
        });

        //Initialized Google SignIn API
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {
                    //Log the login error
                    Log.d(TAG, "onConnectionFailed:" + connectionResult);
                    Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firestoreDb = FirebaseFirestore.getInstance();
        appRepository = AppRepository.getInstance(this.getApplication());
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else
                Log.e(TAG, "Google Sign-In failed.");
                Toast.makeText(ActivitySignIn.this, "Authentication failed 1.", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            // If sign in fails, display a message to the user. If sign in succeeds
            // the auth state listener will be notified and logic to handle the
            // signed in user can be handled in the listener.
            if (task.isSuccessful())
                checkUser();
            else {
                Log.w(TAG, "signInWithCredential", task.getException());
                Toast.makeText(ActivitySignIn.this, "Authentication failed 2.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUser() {
        String userId = firebaseAuth.getCurrentUser().getEmail();
        appRepository.readUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user ->
                            openMainActivity(),
                        error -> {
                            if (error.getClass() == EmptyResultSetException.class)
                                createUserProfile(userId);
                        });
    }

    private void createUserProfile(String userId) {
        final String ID = "id";
        firestoreDb.collection(DATASOURCES)
                .orderBy(ID, Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        long newDatasourceId;
                        if (task.getResult().isEmpty())
                            newDatasourceId = 1;
                        else {
                            newDatasourceId = task.getResult().getDocuments().get(0).getLong(ID) + 1;
                        }
                        createDatasoure(userId, newDatasourceId);
                    }
                    else
                        Log.d(TAG, "Error in reading firestore: " + task.getException());
        });
    }

    private void createDatasoure(String userId, long datasourceId){
        Map<String, Object> datasource = new HashMap<>();
        datasource.put("id", datasourceId);
        datasource.put("userId", userId);
        datasource.put("name", firebaseAuth.getUid());
        datasource.put("updateDate", new Date());

        firestoreDb.collection(DATASOURCES)
                .add(datasource)
                .addOnSuccessListener(documentReference -> {
                    createLocalProfile(userId, datasourceId);
                })
                .addOnFailureListener(exception -> {
                    Log.e(TAG, "Error on adding new datasource : " + exception);
                });
    }

    private void createLocalProfile(String username, long datasourceId){
        User user = new User(0, username, new Date());
        appRepository.createUser(user, newUserId -> {
            Datasource datasource = new Datasource(datasourceId, newUserId, firebaseAuth.getUid(), new Date());
            appRepository.createDatasource(datasource, id -> {
                createDefaulAccount(newUserId, datasourceId);
            });
        });
    }

    private void createDefaulAccount(long userId, long datasourceId){
        appRepository.createDefaultAccount(userId, datasourceId, account -> {
            //Once the default account is created, set it as the selected account in the preference.
            String value = String.valueOf(account._id) + "," + String.valueOf(account.datasourceId);
            Preference pref = new Preference(Preference.SELECTED_ACCOUNT, value);
            appRepository.updatePreference(pref, () -> {});

            //Finally, open the main activity
            openMainActivity();
        });
    }

    private void openMainActivity(){
        startActivity(new Intent(ActivitySignIn.this, ActivityMain.class));
        finish();
    }
}