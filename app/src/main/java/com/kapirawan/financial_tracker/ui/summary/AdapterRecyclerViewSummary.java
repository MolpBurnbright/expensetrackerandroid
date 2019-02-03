package com.kapirawan.financial_tracker.ui.summary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterRecyclerViewSummary extends
        RecyclerView.Adapter<AdapterRecyclerViewSummary.ViewHolder>{

    private static final int HEADER_VIEW = 0;
    private static final int DETAIL_ViEW = 1;
    private static final int FOOTER_VIEW = 2;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewBudget;
        public TextView textViewExpense;
        public TextView textViewRemaining;

        public ViewHolder (View v){
            super(v);
            this.textViewName = v.findViewById(R.id.recyclerview_item_summary_name);
            this.textViewExpense = v.findViewById(R.id.recyclerview_item_summary_expense);
            this.textViewBudget = v.findViewById(R.id.recyclerview_item_summary_budget);
            this.textViewRemaining = v.findViewById(R.id.recyclerview_item_summary_remaining);
        }
    }

    private List<Summary> summaries;

    public AdapterRecyclerViewSummary(){
    }

    public void setSummaryDataset(List<Summary> summary){
        this.summaries = summary;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_recyclerview_item_summary, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos){
        if (pos > 0){
            int item = pos - 1;
            Summary summary = this.summaries.get(item);
            viewHolder.textViewName.setText(summary.name);
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            viewHolder.textViewBudget.setText(formatter.format(summary.totalBudget));
            viewHolder.textViewExpense.setText(formatter.format(summary.totalExpense));
            viewHolder.textViewRemaining.setText(formatter.format(summary.totalRemaining));
        }else{
            viewHolder.textViewName.setText("Item");
            viewHolder.textViewBudget.setText("Budget");
            viewHolder.textViewExpense.setText("Expense");
            viewHolder.textViewRemaining.setText("Remaining");
        }
    }

    @Override
    public int getItemCount() {
        if(this.summaries != null)
            return summaries.size() + 1;
        else
            return 0;
    }

    @Override
    public int getItemViewType(int pos){
        if (pos == 0)
            return HEADER_VIEW;
        else if (pos == getItemCount())
            return FOOTER_VIEW;
        else
            return DETAIL_ViEW;
    }
}