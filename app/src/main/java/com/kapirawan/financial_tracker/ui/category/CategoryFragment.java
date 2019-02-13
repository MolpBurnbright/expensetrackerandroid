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

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.ui._common.ContextMenuRecyclerView;

import java.util.Date;

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
        //TODO: Supply correct Account
        viewModel.init(new Account(1, 0, 0, "My Accoount", new Date()));
        viewModel.getCategories().observe(this, categories  ->
                adapter.setCategories(categories)
        );
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
        ContextMenuRecyclerView.RecyclerViewContextMenuInfo info =
                (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();
        Category category = viewModel.getCategories().getValue().get(info.position);
        switch(item.getItemId()){
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
        return super.onContextItemSelected(item);
    }
}