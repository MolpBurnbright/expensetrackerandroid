package com.kapirawan.financial_tracker.ui.source;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;

import java.util.List;

public class SourceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int SOURCE_VIEW = 0;
    private static final int TITLE_VIEW = 1;
    private static final int BLANK_VIEW = 2;

    private List<Source> sources;

    public SourceListAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if(viewType == SOURCE_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.source_recyclerview_item_source, parent, false);
            holder = new SourceListAdapter.SourceViewHolder(v);
        }else if(viewType == TITLE_VIEW){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.source_recyclerview_title, parent, false);
            holder = new SourceListAdapter.SourceViewHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.source_recyclerview_blank, parent, false);
            holder = new SourceListAdapter.SourceViewHolder(v);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        int viewType = getItemViewType(position);
        if(viewType == SOURCE_VIEW) {
            if (sources != null) {
                SourceViewHolder sourceViewHolder = (SourceViewHolder) holder;
                //Subtract the position by 1 since the first position is occupied by the tile view.
                //Data item begins at 2nd position.
                final Source source = sources.get(position - 1);
                sourceViewHolder.textViewName.setText(source.name);
            }
        }
    }

    public void setCategories(List<Source> sources){
        this.sources = sources;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(sources != null)
            return sources.size() + 2;
        else
            return 2;
    }

    @Override
    public int getItemViewType(int pos){
        if(pos == 0)
            return  TITLE_VIEW;
        else if (sources != null && pos <= sources.size())
            return SOURCE_VIEW;
        else
            return BLANK_VIEW;
    }

    public static class BlankViewHolder extends RecyclerView.ViewHolder {
        private BlankViewHolder (View v){
            super(v);
        }
    }

    class SourceViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;

        private SourceViewHolder(View v){
            super(v);
            textViewName = itemView.findViewById(R.id.textView_sourcename);
            itemView.setOnClickListener(view -> view.showContextMenu());
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        private TitleViewHolder(View v){
            super(v);
        }
    }
}