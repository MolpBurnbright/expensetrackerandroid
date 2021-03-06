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
import com.kapirawan.financial_tracker.activities.ActivityMainViewModel;
import com.kapirawan.financial_tracker.ui.budget.AddBudgetDialog;
import com.kapirawan.financial_tracker.ui.expense.AddExpenseDialog;
import com.kapirawan.financial_tracker.ui.fund.AddFundDialog;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.List;

public class FragmentSummary extends Fragment {
    FragmentSummaryViewModel viewModel;
    List<Sum> expensesSum, budgetsSum;

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
        long userId = ViewModelProviders.of(this.getActivity()).get(ActivityMainViewModel.class).getUser()._id;
        viewModel = ViewModelProviders.of(this).get(FragmentSummaryViewModel.class);
        viewModel.initUserId(userId);
        viewModel.getSelectedAccount().observe(this, selectedAccount ->{
            if (selectedAccount!= null) {
                String[] parsedValues = selectedAccount.value.split(",");
                long accountID = Long.parseLong(parsedValues[0]);
                long accounDatasourceId = Long.parseLong(parsedValues[1]);
                viewModel.init(accountID, accounDatasourceId);
                viewModel.getExpensesSummary().observe(this, sums -> {
                    expensesSum = sums;
                    if (expensesSum != null & budgetsSum != null) {
                        List<Summary> summaries = Summary.getSummaries(expensesSum, budgetsSum);
                        adapter.setBudgetExpenseSummary(summaries);
                    }
                });
                viewModel.getBudgetsSummary().observe(this, sums -> {
                    budgetsSum = sums;
                    if (expensesSum != null & budgetsSum != null) {
                        List<Summary> summaries = Summary.getSummaries(expensesSum, budgetsSum);
                        adapter.setBudgetExpenseSummary(summaries);
                    }
                });
                viewModel.getFundsSummary().observe(this, sum -> adapter.setFundsSummaries(sum));
                viewModel.getAccount().observe(this, account -> {
                    TextView textView = rootView.findViewById(R.id.textview_accountname);
                    if(textView != null)
                        textView.setText(account.name);
                });
                viewModel.getTotalBudget().observe(this, amount -> adapter.setTotalBudget(amount));
                viewModel.getTotalExpense().observe(this, amount -> adapter.setTotalExpense(amount));
                viewModel.getTotalFund().observe(this, amount -> adapter.setTotalFund(amount));
            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        //Remove observer for this fragment so that no duplicate observers will be created
        //when onCreateView is invoked again
        viewModel.getSelectedAccount().removeObservers(this);
    }

}