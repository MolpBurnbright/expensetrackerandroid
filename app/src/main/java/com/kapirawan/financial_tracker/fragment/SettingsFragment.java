package com.kapirawan.financial_tracker.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.kapirawan.financial_tracker.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstantState){
        super.onCreate(savedInstantState);

        //Set the preference xml
        addPreferencesFromResource(R.xml.pref_main);
    }
}
