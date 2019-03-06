package com.kapirawan.financial_tracker.ui.account;

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

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.ui._common.ContextMenuRecyclerView;


public class AccountFragment extends Fragment {
    AccountFragmentViewModel viewModel;
    AccountListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.account_fragment,container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_accountlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        registerForContextMenu(recyclerView);
        adapter = new AccountListAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(AccountFragmentViewModel.class);
        viewModel.getSelectedUser().observe(this, selectedUser -> {
            viewModel.init(Long.parseLong(selectedUser.value));
            viewModel.getSelectedAccount().observe(this, selectedAccount -> {
                String[] parsedValues = selectedAccount.value.split(",");
                adapter.setSelectedAccount(Long.parseLong(parsedValues[0]), Long.parseLong(parsedValues[1]));
            });
            viewModel.getAccounts().observe(this, accounts  ->
                    adapter.setAccounts(accounts)
            );
        });
        rootView.findViewById(R.id.fab_addaccount).setOnClickListener(view -> new AddAccountDialog()
                .show(this.getActivity().getSupportFragmentManager(), "Add Account Dialog"));
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.account_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        ContextMenuRecyclerView.RecyclerViewContextMenuInfo info =
                (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();
        Account account = viewModel.getAccounts().getValue().get(info.position);
        switch(item.getItemId()){
            case R.id.edit:
                ViewModelProviders.of(this.getActivity())
                        .get(EditAccountDialogViewModel.class).init(account);
                new EditAccountDialog().show(this.getFragmentManager(),
                        "Update Account Dialog");
                break;
            case R.id.remove:
                ViewModelProviders.of(this.getActivity())
                        .get(RemoveAccountDialogViewModel.class).init(account);
                new RemoveAccountDialog().show(this.getFragmentManager(),
                        "Remove Account Dialog");
                break;
            case R.id.select:
                viewModel.setSelectedAccount(account);
                break;
        }
        return super.onContextItemSelected(item);
    }
}