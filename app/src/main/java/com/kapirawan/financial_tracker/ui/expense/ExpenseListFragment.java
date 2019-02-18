package com.kapirawan.financial_tracker.ui.expense;

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
import com.kapirawan.financial_tracker.preference.Preference;
import com.kapirawan.financial_tracker.ui._common.ContextMenuRecyclerView;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

public class ExpenseListFragment extends Fragment {
    ExpenseListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.expense_fragment,container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_expenses_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        registerForContextMenu(recyclerView);
        ExpenseListAdapter adapter = new ExpenseListAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(ExpenseListFragmentViewModel.class);
        viewModel.getSelectedAccount().observe(this, selectedAccount -> {
            String[] parsedValues = selectedAccount.value.split(",");
            long accountID = Long.parseLong(parsedValues[0]);
            long accounDatasourceId = Long.parseLong(parsedValues[1]);
            viewModel.init(accountID, accounDatasourceId);
            viewModel.getExpenses().observe(this, expenses  -> adapter.setExpenses(expenses));
            viewModel.getAccount().observe(this, account ->
                    ((TextView)rootView.findViewById(R.id.textview_accountname)).setText(account.name));
        });
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.expense_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getGroupId() == R.id.expense_menu) {
            ContextMenuRecyclerView.RecyclerViewContextMenuInfo info =
                    (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();
            Expense expense = viewModel.getExpenses().getValue().get(info.position);
            switch (item.getItemId()) {
                case R.id.edit_expense:
                    ViewModelProviders.of(this.getActivity())
                            .get(EditExpenseDialogViewModel.class).init(expense);
                    new EditExpenseDialog().show(this.getFragmentManager(),
                            "Update Expense Dialog");
                    break;
                case R.id.remove_expense:
                    ViewModelProviders.of(this.getActivity())
                            .get(RemoveExpenseDialogViewModel.class).init(expense);
                    new RemoveExpenseDialog().show(this.getFragmentManager(),
                            "Update Expense Dialog");
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }
}