package com.kapirawan.financial_tracker.ui.source;

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
import com.kapirawan.financial_tracker.roomdatabase.source.Source;
import com.kapirawan.financial_tracker.ui._common.ContextMenuRecyclerView;

import java.util.Date;

public class SourceFragment extends Fragment {
    SourceFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.source_fragment,container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_sourcelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        registerForContextMenu(recyclerView);
        SourceListAdapter adapter = new SourceListAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(SourceFragmentViewModel.class);
        //TODO: Supply correct Account
        viewModel.init(new Account(1, 0, 0, "My Accoount", new Date()));
        viewModel.getCategories().observe(this, sources  ->
                adapter.setCategories(sources)
        );
        rootView.findViewById(R.id.fab_addsource).setOnClickListener(view -> new AddSourceDialog()
                .show(this.getActivity().getSupportFragmentManager(), "Add Source Dialog"));
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.source_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getGroupId() == R.id.source_menu) {
            ContextMenuRecyclerView.RecyclerViewContextMenuInfo info =
                    (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();
            Source source = viewModel.getCategories().getValue().get(info.position);
            switch (item.getItemId()) {
                case R.id.edit_source:
                    ViewModelProviders.of(this.getActivity())
                            .get(EditSourceDialogViewModel.class)
                            .init(source);
                    new EditSourceDialog().show(this.getFragmentManager(),
                            "Update Source Dialog");
                    break;
                case R.id.remove_source:
                    ViewModelProviders.of(this.getActivity())
                            .get(RemoveSourceDialogViewModel.class).init(source);
                    new RemoveSourceDialog().show(this.getFragmentManager(),
                            "Remove Source Dialog");
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }
}