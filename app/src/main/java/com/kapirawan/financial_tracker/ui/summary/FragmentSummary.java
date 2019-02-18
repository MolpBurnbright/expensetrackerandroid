package com.kapirawan.financial_tracker.ui.summary;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.preference.Preference;
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
        initFloatingButtons(rootView);
        initViewModel(rootView);
        return rootView;
    }

    private void initFloatingButtons(View rootView){
        FloatingActionButton fabExpense = rootView.findViewById(R.id.fab_expense);
        FloatingActionButton fabBudget = rootView.findViewById(R.id.fab_budget);
        FloatingActionButton fabFund = rootView.findViewById(R.id.fab_fund);
        fabExpense.hide();
        rootView.findViewById(R.id.textview_expense).setVisibility(View.INVISIBLE);
        fabBudget.hide();
        rootView.findViewById(R.id.textview_budget).setVisibility(View.INVISIBLE);
        fabFund.hide();
        rootView.findViewById(R.id.textview_fund).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.fab_controller).setOnClickListener(view ->{
            if (fabExpense.getVisibility() == View.VISIBLE) {
                fabExpense.hide();
                rootView.findViewById(R.id.textview_expense).setVisibility(View.INVISIBLE);
                fabBudget.hide();
                rootView.findViewById(R.id.textview_budget).setVisibility(View.INVISIBLE);
                fabFund.hide();
                rootView.findViewById(R.id.textview_fund).setVisibility(View.INVISIBLE);

            } else {
                fabExpense.show();
                rootView.findViewById(R.id.textview_expense).setVisibility(View.VISIBLE);
                fabBudget.show();
                rootView.findViewById(R.id.textview_budget).setVisibility(View.VISIBLE);
                fabFund.show();
                rootView.findViewById(R.id.textview_fund).setVisibility(View.VISIBLE);
            }
        });
        fabExpense.setOnClickListener(view -> new AddExpenseDialog()
                .show(this.getActivity().getSupportFragmentManager(), "Add Expense Dialog"));
        fabBudget.setOnClickListener(view -> new AddBudgetDialog()
                .show(this.getActivity().getSupportFragmentManager(), "Add Budget Dialog"));
        fabFund.setOnClickListener(view -> new AddFundDialog().
                show(getActivity().getSupportFragmentManager(), "Add Fund Dialog"));
    }

    private void initViewModel(View rootView){
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_summary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        AdapterRecyclerViewSummary adapter = new AdapterRecyclerViewSummary();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(FragmentSummaryViewModel.class);
        viewModel.getSelectedAccount().observe(this, selectedAccount ->{
            String[] parsedValues = selectedAccount.value.split(",");
            long accountID = Long.parseLong(parsedValues[0]);
            long accounDatasourceId = Long.parseLong(parsedValues[1]);
            viewModel.init(accountID, accounDatasourceId);
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
            viewModel.getAccount().observe(this, account -> {
                ((TextView) rootView.findViewById(R.id.textview_accountname)).setText(account.name);
            });
        });
    }
}