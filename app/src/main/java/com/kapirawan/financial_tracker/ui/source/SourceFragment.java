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
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;
import com.kapirawan.financial_tracker.ui._common.ContextMenuRecyclerView;

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

        viewModel.getSelectedAccount().observe(this, selectedAccount -> {
            if(selectedAccount != null) {
                String[] parsedValues = selectedAccount.value.split(",");
                long accountID = Long.parseLong(parsedValues[0]);
                long accounDatasourceId = Long.parseLong(parsedValues[1]);
                viewModel.init(accountID, accounDatasourceId);
                viewModel.getSources().observe(this, sources -> adapter.setCategories(sources));
                viewModel.getAccount().observe(this, account -> {
                    TextView textView = rootView.findViewById(R.id.textview_accountname);
                    if(textView != null)
                        textView.setText(account.name);
                });
            }
        });

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
            // One is subtracted from the position, since the first position is occupied by title
            // view in RecyclerView
            Source source = viewModel.getSources().getValue().get(info.position - 1);
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

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        //Remove observer for this fragment so that no duplicate observers will be created
        //when onCreateView is invoked again
        viewModel.getSelectedAccount().removeObservers(this);
        viewModel.getAccount().removeObservers(this);
        viewModel.getSources().removeObservers(this);
    }
}