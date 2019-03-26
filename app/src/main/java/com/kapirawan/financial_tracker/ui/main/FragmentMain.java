package com.kapirawan.financial_tracker.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.activities.ActivityMainViewModel;

public class FragmentMain extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_main_fragment,container
                , false);
        ViewPager viewPager = rootView.findViewById(R.id.viewPager);
        PageAdapter adapter = new PageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        return rootView;
    }
}