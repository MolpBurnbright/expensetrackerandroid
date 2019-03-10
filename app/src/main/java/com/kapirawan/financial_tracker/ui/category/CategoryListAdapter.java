package com.kapirawan.financial_tracker.ui.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int CATEGORY_VIEW = 0;
    private static final int TITLE_VIEW = 1;
    private static final int BLANK_VIEW = 2;

    private List<Category> categories;

    public CategoryListAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if(viewType == CATEGORY_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_recyclerview_item_category, parent, false);
            holder = new CategoryViewHolder(v);
        }else if(viewType == TITLE_VIEW){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_recyclerview_title, parent, false);
            holder = new TitleViewHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_recyclerview_blank, parent, false);
            holder = new BlankViewHolder(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        int viewType = getItemViewType(position);
        if(viewType == CATEGORY_VIEW) {
            CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
            if (categories != null) {
                //Subtract the position by 1 since the first position is occupied by the tile view.
                //Data item begins at 2nd position.
                final Category category = categories.get(position - 1);
                categoryViewHolder.textViewName.setText(category.name);
            }
        }
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(categories != null)
            return categories.size() + 2;
        else
            return 2;
    }

    @Override
    public int getItemViewType(int pos){
        if(pos == 0)
            return  TITLE_VIEW;
        else if (categories != null && pos <= categories.size())
            return CATEGORY_VIEW;
        else
            return BLANK_VIEW;
    }

    public static class BlankViewHolder extends RecyclerView.ViewHolder {
        private BlankViewHolder (View v){
            super(v);
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;

        private CategoryViewHolder(View v){
            super(v);
            textViewName = itemView.findViewById(R.id.textView_categoryname);
            itemView.setOnClickListener(view -> view.showContextMenu());
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        private TitleViewHolder(View v){
            super(v);
        }
    }
}