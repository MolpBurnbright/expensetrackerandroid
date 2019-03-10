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

public class BudgetListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int BUDGET_VIEW = 0;
    private static final int TITLE_VIEW = 1;
    private static final int BLANK_VIEW = 2;

    private List<Budget> budgets;

    public BudgetListAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if(viewType == BUDGET_VIEW){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.budget_recyclerview_item_budget, parent, false);
            holder = new BudgetViewHolder(v);
        }else if(viewType == TITLE_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.budget_recyclerview_title, parent, false);
            holder = new TitleViewHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.budget_recyclerview_blank, parent, false);
            holder = new BlankViewHolder(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        int viewType = getItemViewType(position);
        if(viewType == BUDGET_VIEW) {
            BudgetViewHolder budgetViewHolder = (BudgetViewHolder) holder;
            if (budgets != null) {
                //Subtract the position by 1 since the first position is occupied by the tile view.
                //Data item begins at 2nd position.
                final Budget budget = budgets.get(position - 1);
                budgetViewHolder.textViewCategory.setText(budget.type);
                budgetViewHolder.textViewDetails.setText(budget.details);
                budgetViewHolder.textViewAmount.setText(
                        (new DecimalFormat("#,###,##0.00")).format(budget.amount));
                budgetViewHolder.textViewDate.setText(
                        (new SimpleDateFormat("dd-MMM-yyyy")).format(budget.date));
            } else {
                budgetViewHolder.textViewDetails.setText("No accounts yet");
            }
        }
    }

    public void setBudgets(List<Budget> accounts){
        this.budgets = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(budgets != null)
            return budgets.size() + 2;
        else
            return 2;
    }

    @Override
    public int getItemViewType(int pos){
        if(pos == 0)
            return  TITLE_VIEW;
        else if (budgets != null && pos <= budgets.size())
            return BUDGET_VIEW;
        else
            return BLANK_VIEW;
    }

    public static class BlankViewHolder extends RecyclerView.ViewHolder {

        private BlankViewHolder (View v){
            super(v);
        }
    }

    public static class BudgetViewHolder extends RecyclerView.ViewHolder {
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

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        private TitleViewHolder(View v){
            super(v);
        }
    }
}