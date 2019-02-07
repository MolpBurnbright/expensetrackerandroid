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
        viewModel.init(1, 0);
        viewModel.getFunds().observe(this, funds  ->
                adapter.setFunds(funds)
        );
        viewModel.getAccount().observe(this, account ->
                ((TextView)rootView.findViewById(R.id.textview_accountname)).setText(account.name));
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        ContextMenuRecyclerView.RecyclerViewContextMenuInfo info =
                (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();
        Fund fund = viewModel.getFunds().getValue().get(info.position);
        switch(item.getItemId()){
            case R.id.edit:
                ViewModelProviders.of(this.getActivity())
                        .get(EditFundDialogViewModel.class).init(fund);
                new EditFundDialog().show(this.getFragmentManager(),
                        "Update Fund Dialog");
                break;
            case R.id.remove:
                ViewModelProviders.of(this.getActivity())
                        .get(RemoveFundDialogViewModel.class).init(fund);
                new RemoveFundDialog().show(this.getFragmentManager(),
                        "Update Fund Dialog");
                break;
        }
        return super.onContextItemSelected(item);
    }
}