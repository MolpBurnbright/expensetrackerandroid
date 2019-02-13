package com.kapirawan.financial_tracker.ui.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>{

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;

        private CategoryViewHolder(View v){
            super(v);
            textViewName = itemView.findViewById(R.id.textView_categoryname);
            itemView.setOnClickListener(view -> view.showContextMenu());
        }
    }

    private List<Category> categories;

    public CategoryListAdapter() {
    }

    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_recyclerview_item_category, parent, false);
        return new CategoryListAdapter.CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryViewHolder holder, int position){
        if(categories != null){
            final Category category = categories.get(position);
            holder.textViewName.setText(category.name);
        }
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(categories != null)
            return categories.size();
        else
            return 0;
    }
}