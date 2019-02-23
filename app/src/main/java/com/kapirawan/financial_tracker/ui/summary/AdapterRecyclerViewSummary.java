package com.kapirawan.financial_tracker.ui.summary;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerViewSummary extends
        RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int BUDGET_EXPENSE_VIEW = 1;
    private static final int FUND_VIEW = 2;

    private List<Summary> budgetExpenseSummaries;
    private List<Sum> fundsSummaries;

    public AdapterRecyclerViewSummary(){
    }

    public void setBudgetExpenseSummary(List<Summary> summary){
        this.budgetExpenseSummaries = summary;
        notifyDataSetChanged();
    }

    public void setFundsSummaries(List<Sum> summary){
        fundsSummaries = summary;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder holder;
        if (viewType == BUDGET_EXPENSE_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.summary_recyclerview_expensebudget_container, parent, false);
            holder = new ViewHolderBudgetExpense(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.summary_recyclerview_fund_container, parent, false);
            holder = new ViewHolderFund(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos){
        int type = getItemViewType(pos);
        if (type == BUDGET_EXPENSE_VIEW) {
            LinearLayout container = ((ViewHolderBudgetExpense) viewHolder).linearLayoutItems;
            container.removeAllViews();
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            for (Summary sum : budgetExpenseSummaries) {
                View item = LayoutInflater.from(viewHolder.itemView.getContext())
                        .inflate(R.layout.summary_recyclerview_expensebudget_item, null);
                ((TextView) item.findViewById(R.id.textview_name)).setText(sum.name);
                ((TextView) item.findViewById(R.id.textview_budget)).setText(formatter.format(sum.totalBudget));
                ((TextView) item.findViewById(R.id.textview_expense)).setText(formatter.format(sum.totalExpense));
                ((TextView) item.findViewById(R.id.textview_remaining)).setText(formatter.format(sum.totalRemaining));
                container.addView(item);
            }
        }else if (type == FUND_VIEW){
            LinearLayout container = ((ViewHolderFund) viewHolder).linearLayoutItems;
            container.removeAllViews();
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            for (Sum sum : fundsSummaries) {
                View item = LayoutInflater.from(viewHolder.itemView.getContext())
                        .inflate(R.layout.summary_recyclerview_fund_item, null);
                ((TextView) item.findViewById(R.id.textview_name)).setText(sum.name);
                ((TextView) item.findViewById(R.id.textview_fund)).setText(formatter.format(sum.amount));
                container.addView(item);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (budgetExpenseSummaries != null)
            count += 1;
        if (fundsSummaries != null)
            count += 1;
        return count;
    }

    @Override
    public int getItemViewType(int pos){
        ArrayList<Integer> types = new ArrayList();

        if(budgetExpenseSummaries != null)
            types.add(BUDGET_EXPENSE_VIEW);

        if(fundsSummaries != null)
            types.add(FUND_VIEW);

        return types.get(pos).intValue();
    }

    public static class ViewHolderBudgetExpense extends RecyclerView.ViewHolder {
        public LinearLayout linearLayoutItems;

        public ViewHolderBudgetExpense (View v){
            super(v);
            this.linearLayoutItems = v.findViewById(R.id.linearlayout_items);
        }
    }

    public static class ViewHolderFund extends RecyclerView.ViewHolder {
        public LinearLayout linearLayoutItems;

        public ViewHolderFund (View v){
            super(v);
            this.linearLayoutItems = v.findViewById(R.id.linearlayout_items);
        }
    }
}