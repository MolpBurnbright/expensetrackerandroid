package com.kapirawan.financial_tracker.ui.budget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class BudgetListAdapter extends RecyclerView.Adapter<BudgetListAdapter.BudgetViewHolder>{

    class BudgetViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewCategory;
        private final TextView textViewDetails;
        private final TextView textViewDate;
        private final TextView textViewAmount;

        private BudgetViewHolder(View v){
            super(v);
            textViewCategory = itemView.findViewById(R.id.textView_type);
            textViewDetails = itemView.findViewById(R.id.textView_details);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewAmount = itemView.findViewById(R.id.textView_amount);
            itemView.setOnClickListener(view -> view.showContextMenu());
        }
    }

    private List<Budget> budgets;

    public BudgetListAdapter() {
    }

    @Override
    public BudgetListAdapter.BudgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budget_recyclerview_item_budget, parent, false);
        return new BudgetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BudgetListAdapter.BudgetViewHolder holder, int position){
        if(budgets != null){
            final Budget budget = budgets.get(position);
            holder.textViewCategory.setText(budget.type);
            holder.textViewDetails.setText(budget.details);
            holder.textViewAmount.setText(
                    (new DecimalFormat("#,###,##0.00")).format(budget.amount));
            holder.textViewDate.setText(
                    (new SimpleDateFormat("dd-MMM-yyyy")).format(budget.date));
        }else {
            holder.textViewDetails.setText("No accounts yet");
        }
    }

    public void setBudgets(List<Budget> accounts){
        this.budgets = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(budgets != null)
            return budgets.size();
        else
            return 0;
    }
}