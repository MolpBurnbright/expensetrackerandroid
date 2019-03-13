package com.kapirawan.financial_tracker.ui.expense;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.ui._common.ContextMenuRecyclerView;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

public class ExpenseListFragment extends Fragment implements ExpenseListAdapter.OnBindSpinnerListener {
    ExpenseListFragmentViewModel viewModel;
    ExpenseListAdapter expenseListAdapter;
    Spinner spinnerCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.expense_fragment,container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_expenses_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        registerForContextMenu(recyclerView);
        expenseListAdapter = new ExpenseListAdapter(this);
        recyclerView.setAdapter(expenseListAdapter);
        viewModel = ViewModelProviders.of(this).get(ExpenseListFragmentViewModel.class);
        viewModel.getSelectedAccount().observe(this, selectedAccount -> {
            if(selectedAccount != null) {
                String[] parsedValues = selectedAccount.value.split(",");
                long accountID = Long.parseLong(parsedValues[0]);
                long accounDatasourceId = Long.parseLong(parsedValues[1]);
                viewModel.init(accountID, accounDatasourceId);
                viewModel.getExpenses().observe(this, expenses -> expenseListAdapter.setExpenses(expenses));
                viewModel.getAccount().observe(this, account -> {
                    TextView textView = rootView.findViewById(R.id.textview_accountname);
                    if(textView != null)
                        textView.setText(account.name);
                });
                initSpinnerCategories();
            }
        });
        rootView.findViewById(R.id.fab_addexpense).setOnClickListener(view -> new AddExpenseDialog()
                .show(this.getActivity().getSupportFragmentManager(), "Add Expense Dialog"));
        return rootView;
    }

    private void initSpinnerCategories(){
        ExpenseListFragment parentFragment = this;
        this.viewModel.getExpenseCategories().observe(this, categories -> {
            categories.add(0, "(All)");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                    android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategories.setAdapter(adapter);
            spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (adapter.getItem(i).equals("(All)")) {
                        viewModel.getExpenses().removeObservers(parentFragment);
                        viewModel.setExpenseList();
                        viewModel.getExpenses().observe(parentFragment,
                                expenses -> expenseListAdapter.setExpenses(expenses));
                    } else {
                        viewModel.getExpenses().removeObservers(parentFragment);
                        viewModel.setExpenseList(adapter.getItem(i));
                        viewModel.getExpenses().observe(parentFragment,
                                expenses -> expenseListAdapter.setExpenses(expenses));
                    }
                    //TODO: filter expenses based on selected categories.
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerCategories.setSelection(0);
        });
    }

    @Override
    public void onBindSpinner(Spinner spinner){
        spinnerCategories = spinner;
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
            // One is subtracted from the position, since the first position is occupied by title
            // view in RecyclerView
            Expense expense = viewModel.getExpenses().getValue().get(info.position - 1);
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

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        //Remove observer for this fragment so that no duplicate observers will be created
        //when onCreateView is invoked again
        viewModel.getSelectedAccount().removeObservers(this);
        viewModel.getAccount().removeObservers(this);
        viewModel.getExpenses().removeObservers(this);
        viewModel.getExpenseCategories().removeObservers(this);
    }
}