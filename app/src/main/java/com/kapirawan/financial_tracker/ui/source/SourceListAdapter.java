package com.kapirawan.financial_tracker.ui.source;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;

import java.util.List;

public class SourceListAdapter extends RecyclerView.Adapter<SourceListAdapter.SourceViewHolder>{

    class SourceViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;

        private SourceViewHolder(View v){
            super(v);
            textViewName = itemView.findViewById(R.id.textView_sourcename);
            itemView.setOnClickListener(view -> view.showContextMenu());
        }
    }

    private List<Source> sources;

    public SourceListAdapter() {
    }

    @Override
    public SourceListAdapter.SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.source_recyclerview_item_source, parent, false);
        return new SourceListAdapter.SourceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SourceListAdapter.SourceViewHolder holder, int position){
        if(sources != null){
            final Source source = sources.get(position);
            holder.textViewName.setText(source.name);
        }
    }

    public void setCategories(List<Source> sources){
        this.sources = sources;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(sources != null)
            return sources.size();
        else
            return 0;
    }
}