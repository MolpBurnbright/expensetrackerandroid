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
    private static final int BALANCE_VIEW = 3;
    private static final int BLANK_VIEW = 4;
    private static final int TITLE_VIEW = 5;

    private List<Summary> budgetExpenseSummaries;
    private List<Sum> fundsSummaries;
    private double totalExpense, totalFund, totalBudget;

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

    public void setTotalExpense(Double amount){
        if(amount != null) {
            totalExpense = amount;
            notifyDataSetChanged();
        }
    }

    public void setTotalFund(Double amount){
        if(amount != null) {
            totalFund = amount;
            notifyDataSetChanged();
        }
    }

    public void setTotalBudget(Double amount){
        if(amount != null) {
            totalBudget = amount;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder holder;
        if (viewType == BUDGET_EXPENSE_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.summary_recyclerview_expensebudget_container, parent, false);
            holder = new ViewHolderBudgetExpense(v);
        }else if(viewType == FUND_VIEW){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.summary_recyclerview_fund_container, parent, false);
            holder = new ViewHolderFund(v);
        }else if (viewType == BALANCE_VIEW){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.summary_recyclerview_balance_container, parent, false);
            holder = new ViewHolderBalance(v);
        }else if (viewType == BLANK_VIEW){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.summary_recyclerview_blank_container, parent, false);
            holder = new ViewHolderBlank(v);
        }else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.summary_recyclerview_title_container, parent, false);
            holder = new ViewHolderTitle(v);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos){
        int type = getItemViewType(pos);
        if(type == BUDGET_EXPENSE_VIEW) {
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
        }else if(type == FUND_VIEW){
            LinearLayout container = ((ViewHolderFund) viewHolder).linearLayoutItems;
            container.removeAllViews();
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            double totalFunds = 0;
            for (Sum sum : fundsSummaries) {
                View item = LayoutInflater.from(viewHolder.itemView.getContext())
                        .inflate(R.layout.summary_recyclerview_fund_item, null);
                ((TextView) item.findViewById(R.id.textview_name)).setText(sum.name);
                ((TextView) item.findViewById(R.id.textview_fund)).setText(formatter.format(sum.amount));
                container.addView(item);
            }
            View item = LayoutInflater.from(viewHolder.itemView.getContext())
                    .inflate(R.layout.summary_recyclerview_fund_item, null);
            ((TextView) item.findViewById(R.id.textview_name)).setText("Total");
            ((TextView) item.findViewById(R.id.textview_fund)).setText(formatter.format(totalFunds));
            container.addView(item);

        }else if(type == BALANCE_VIEW){
            double balance = totalFund - totalExpense;
            double unallocated = totalFund - totalBudget;
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            ViewHolderBalance view = (ViewHolderBalance) viewHolder;
            view.textViewTotalBalance.setText(formatter.format(balance));
            view.textViewTotalUnallocated.setText(formatter.format(unallocated));
        }
    }

    @Override
    public int getItemCount() {
        int count = 3;

        if (budgetExpenseSummaries != null)
            count += 1;
        if (fundsSummaries != null)
            count += 1;
        return count;
    }

    @Override
    public int getItemViewType(int pos){
        ArrayList<Integer> types = new ArrayList();

        types.add(TITLE_VIEW);

        if(budgetExpenseSummaries != null)
            types.add(BUDGET_EXPENSE_VIEW);

        if(fundsSummaries != null)
            types.add(FUND_VIEW);

        types.add(BALANCE_VIEW);
        types.add(BLANK_VIEW);

        return types.get(pos).intValue();
    }

    public static class ViewHolderBalance extends RecyclerView.ViewHolder {
        public TextView textViewTotalBalance;
        public TextView textViewTotalUnallocated;

        public ViewHolderBalance (View v){
            super(v);
            this.textViewTotalBalance = v.findViewById(R.id.textview_balance);
            this.textViewTotalUnallocated = v.findViewById(R.id.textview_unallocated);
        }
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

    public static class ViewHolderBlank extends RecyclerView.ViewHolder {

        public ViewHolderBlank (View v){
            super(v);
        }
    }

    public static class ViewHolderTitle extends RecyclerView.ViewHolder {

        public ViewHolderTitle (View v){
            super(v);
        }
    }

}