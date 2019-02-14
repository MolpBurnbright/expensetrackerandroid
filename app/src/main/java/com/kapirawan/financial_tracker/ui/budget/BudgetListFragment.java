package com.kapirawan.financial_tracker.ui.budget;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.ui._common.ContextMenuRecyclerView;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;

public class BudgetListFragment extends Fragment {
    BudgetListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.budget_fragment,container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_budget_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        registerForContextMenu(recyclerView);
        BudgetListAdapter adapter = new BudgetListAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(BudgetListFragmentViewModel.class);
        viewModel.init(1, 0);
        viewModel.getBudgets().observe(this, budgets  ->
                adapter.setBudgets(budgets)
        );
        viewModel.getAccount().observe(this, account ->
                ((TextView)rootView.findViewById(R.id.textview_accountname)).setText(account.name));
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.budget_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getGroupId() == R.id.budget_menu) {
            ContextMenuRecyclerView.RecyclerViewContextMenuInfo info =
                    (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();
            Budget budget = viewModel.getBudgets().getValue().get(info.position);
            switch (item.getItemId()) {
                case R.id.edit_budget:
                    ViewModelProviders.of(this.getActivity())
                            .get(EditBudgetDialogViewModel.class).init(budget);
                    new EditBudgetDialog().show(this.getFragmentManager(),
                            "Update Budget Dialog");
                    break;
                case R.id.remove_budget:
                    ViewModelProviders.of(this.getActivity())
                            .get(RemoveBudgetDialogViewModel.class).init(budget);
                    new RemoveBudgetDialog().show(this.getFragmentManager(),
                            "Update Budget Dialog");
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }
}