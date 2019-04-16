package com.kapirawan.financial_tracker.ui.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.activities.ActivityMainViewModel;
import com.kapirawan.financial_tracker.activities.ActivitySignIn;

public class SettingsFragment extends Fragment {
    String TAG = "SettingsFragment";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private GoogleApiClient googleApiClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        ActivityMainViewModel activityMainViewModel = ViewModelProviders.of(this.getActivity()).get(ActivityMainViewModel.class);
        firebaseAuth = activityMainViewModel.getFirebaseAuth();
        firebaseUser = activityMainViewModel.getFirebaseUser();
        googleApiClient = activityMainViewModel.getGoogleApiClient();
        View rootView = inflater.inflate(R.layout.settings_fragment,container, false);
        Button button = rootView.findViewById(R.id.button_signinsignout);
        TextView textView = rootView.findViewById(R.id.textview_userinfo);
        if(firebaseUser != null) {
            textView.setText("You are signed in as " + firebaseUser.getDisplayName());
            button.setText("Sign Out");
        }
        else {
            textView.setText("You are not signed in.");
            button.setText("Sign In");
        }
        button.setOnClickListener(v -> {
            if(firebaseUser != null)
                signOut();
            else
                signIn();
        });
        return rootView;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    private void signIn(){
    }

    private void signOut(){
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient);
        startActivity(new Intent(this.getActivity(), ActivitySignIn.class));
        this.getActivity().finish();
    }
}