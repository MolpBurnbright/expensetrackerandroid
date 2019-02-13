package com.kapirawan.financial_tracker.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kapirawan.financial_tracker.R;

public class FragmentMain extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_main_fragment,container
                , false);
        ViewPager viewPager = rootView.findViewById(R.id.viewPager);
        PageAdapter adapter = new PageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position){
                Log.d("Debuging PagViewer", "page selection has change, position: " + position);
                invalidateFragmentMenus(position);
            }

            private void invalidateFragmentMenus(int position){
                for(int i = 0; i < adapter.getCount(); i++){
                    adapter.getItem(i).setHasOptionsMenu(i == position);
                }
//                invalidateOptionsMenu(); //or respectively its support method.
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return rootView;
    }
}