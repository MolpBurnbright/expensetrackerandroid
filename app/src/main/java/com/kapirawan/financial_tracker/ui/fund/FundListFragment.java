package com.kapirawan.financial_tracker.ui.fund;

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
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;

public class FundListFragment extends Fragment {
    FundListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fund_fragment,container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_fund_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        registerForContextMenu(recyclerView);
        FundListAdapter adapter = new FundListAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(FundListFragmentViewModel.class);
        viewModel.getSelectedAccount().observe(this, selectedAccount -> {
            if(selectedAccount != null) {
                String[] parsedValues = selectedAccount.value.split(",");
                long accountID = Long.parseLong(parsedValues[0]);
                long accounDatasourceId = Long.parseLong(parsedValues[1]);
                viewModel.init(accountID, accounDatasourceId);
                viewModel.getFunds().observe(this, funds -> adapter.setFunds(funds));
                viewModel.getAccount().observe(this, account -> {
                    TextView textView = rootView.findViewById(R.id.textview_accountname);
                    if(textView != null)
                        textView.setText(account.name);
                });
            }
        });
        rootView.findViewById(R.id.fab_addfund).setOnClickListener(view -> new AddFundDialog()
                .show(this.getActivity().getSupportFragmentManager(), "Add Fund Dialog"));

        return rootView;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.fund_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getGroupId() == R.id.fund_menu) {
            ContextMenuRecyclerView.RecyclerViewContextMenuInfo info =
                    (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();
            // One is subtracted from the position, since the first position is occupied by title
            // view in RecyclerView
            Fund fund = viewModel.getFunds().getValue().get(info.position - 1);
            switch (item.getItemId()) {
                case R.id.edit_fund:
                    ViewModelProviders.of(this.getActivity())
                            .get(EditFundDialogViewModel.class).init(fund);
                    new EditFundDialog().show(this.getFragmentManager(),
                            "Update Fund Dialog");
                    break;
                case R.id.remove_fund:
                    ViewModelProviders.of(this.getActivity())
                            .get(RemoveFundDialogViewModel.class).init(fund);
                    new RemoveFundDialog().show(this.getFragmentManager(),
                            "Update Fund Dialog");
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }
}