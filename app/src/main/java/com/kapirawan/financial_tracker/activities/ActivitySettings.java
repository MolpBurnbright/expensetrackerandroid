package com.kapirawan.financial_tracker.activities;

import android.app.Activity;
import android.os.Bundle;

import com.kapirawan.financial_tracker.fragment.SettingsFragment;

public class ActivitySettings extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Set the SettingsFragment as the main content
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
