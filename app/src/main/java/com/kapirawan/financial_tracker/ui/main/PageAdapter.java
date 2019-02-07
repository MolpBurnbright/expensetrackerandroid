package com.kapirawan.financial_tracker.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kapirawan.financial_tracker.ui.budget.BudgetListFragment;
import com.kapirawan.financial_tracker.ui.expense.ExpenseListFragment;
import com.kapirawan.financial_tracker.ui.fund.FundListFragment;
import com.kapirawan.financial_tracker.ui.summary.FragmentSummary;

public class PageAdapter extends FragmentPagerAdapter {

    private int NUM_ITEMS = 4;

    public PageAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public int getCount(){
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentSummary();
                break;
            case 1:
                fragment = new ExpenseListFragment();
                break;
            case 2:
                fragment = new BudgetListFragment();
                break;
            case 3:
                fragment = new FundListFragment();
                break;
        }
        return fragment;
    }
}
