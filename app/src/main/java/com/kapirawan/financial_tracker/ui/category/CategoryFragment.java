package com.kapirawan.financial_tracker.ui.category;

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
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.ui._common.ContextMenuRecyclerView;


public class CategoryFragment extends Fragment {
    CategoryFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.category_fragment,container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_categorylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        registerForContextMenu(recyclerView);
        CategoryListAdapter adapter = new CategoryListAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(CategoryFragmentViewModel.class);
        viewModel.getSelectedAccount().observe(this, selectedAccount -> {
            if(selectedAccount != null) {
                String[] parsedValues = selectedAccount.value.split(",");
                long accountID = Long.parseLong(parsedValues[0]);
                long accounDatasourceId = Long.parseLong(parsedValues[1]);
                viewModel.init(accountID, accounDatasourceId);
                viewModel.getCategories().observe(this, categories -> adapter.setCategories(categories));
                viewModel.getAccount().observe(this, account -> {
                    TextView textView = rootView.findViewById(R.id.textview_accountname);
                    if(textView != null)
                        textView.setText(account.name);
                });
            }
        });

        rootView.findViewById(R.id.fab_addcategory).setOnClickListener(view -> new AddCategoryDialog()
                .show(this.getActivity().getSupportFragmentManager(), "Add Category Dialog"));
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.category_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getGroupId() == R.id.category_menu) {
            ContextMenuRecyclerView.RecyclerViewContextMenuInfo info =
                    (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();
            // One is subtracted from the position, since the first position is occupied by title
            // view in RecyclerView
            Category category = viewModel.getCategories().getValue().get(info.position - 1);
            switch (item.getItemId()) {
                case R.id.edit_category:
                    ViewModelProviders.of(this.getActivity())
                            .get(EditCategoryDialogViewModel.class)
                            .init(category);
                    new EditCategoryDialog().show(this.getFragmentManager(),
                            "Update Category Dialog");
                    break;
                case R.id.remove_category:
                    ViewModelProviders.of(this.getActivity())
                            .get(RemoveCategoryDialogViewModel.class).init(category);
                    new RemoveCategoryDialog().show(this.getFragmentManager(),
                            "Remove Category Dialog");
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
    }
}