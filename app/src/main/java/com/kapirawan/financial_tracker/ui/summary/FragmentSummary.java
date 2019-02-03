package com.kapirawan.financial_tracker.ui.summary;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.ui.budget.AddBudgetDialog;
import com.kapirawan.financial_tracker.ui.expense.AddExpenseDialog;
import com.kapirawan.financial_tracker.ui.fund.AddFundDialog;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.List;

public class FragmentSummary extends Fragment {
    FragmentSummaryViewModel viewModel;
    List<Sum> expensesSum, budgetsSum, fundsSum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.summary_fragment_summary,container
                , false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_summary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        AdapterRecyclerViewSummary adapter = new AdapterRecyclerViewSummary();
        recyclerView.setAdapter(adapter);
        initFloatingButtons(rootView);
        viewModel = ViewModelProviders.of(this).get(FragmentSummaryViewModel.class);
        viewModel.init(1, 0);
        viewModel.getExpensesSummary().observe(this, sums -> {
            expensesSum = sums;
            if(expensesSum != null & budgetsSum != null){
                List<Summary> summaries = Summary.getSummaries(expensesSum, budgetsSum);
                adapter.setSummaryDataset(summaries);
            }
        });
        viewModel.getBudgetsSummary().observe(this, sums -> {
            budgetsSum = sums;
            if(expensesSum != null & budgetsSum != null) {
                List<Summary> summaries = Summary.getSummaries(expensesSum, budgetsSum);
                adapter.setSummaryDataset(summaries);
            }
        });
        viewModel.getFundsSummary().observe(this, sum -> {
            fundsSum = sum;
        });
        return rootView;
    }

    private void initFloatingButtons(View rootView){
        FloatingActionButton fabExpense = rootView.findViewById(R.id.fab_expense);
        FloatingActionButton fabBudget = rootView.findViewById(R.id.fab_budget);
        FloatingActionButton fabFund = rootView.findViewById(R.id.fab_fund);
        rootView.findViewById(R.id.fab_controller).setOnClickListener(view ->{
            if (fabExpense.getVisibility() == View.VISIBLE) {
                fabExpense.hide();
                fabBudget.hide();
                fabFund.hide();
            } else {
                fabExpense.show();
                fabBudget.show();
                fabFund.show();
            }
        });
        fabExpense.setOnClickListener(view -> new AddExpenseDialog()
                .show(this.getActivity().getSupportFragmentManager(), "Add Expense Dialog"));
        fabBudget.setOnClickListener(view -> new AddBudgetDialog()
                .show(this.getActivity().getSupportFragmentManager(), "Add Budget Dialog"));
        fabFund.setOnClickListener(view -> new AddFundDialog().
                show(getActivity().getSupportFragmentManager(), "Add Fund Dialog"));
    }
}