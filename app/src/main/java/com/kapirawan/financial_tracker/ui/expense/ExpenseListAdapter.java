package com.kapirawan.financial_tracker.ui.expense;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private OnBindSpinnerListener onBindSpinnerListener;
    private static final int EXPENSE_VIEW = 0;
    private static final int TITLE_VIEW = 1;
    private static final int BLANK_VIEW = 2;
    private List<Expense> expenses;

    public ExpenseListAdapter(OnBindSpinnerListener listener) {
        onBindSpinnerListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if(viewType == EXPENSE_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.expense_recyclerview_item_expense, parent, false);
            holder = new ExpenseViewHolder(v);
        }else if(viewType == TITLE_VIEW){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.expense_recyclerview_title, parent, false);
            holder = new TitleViewHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.expense_recyclerview_blank, parent, false);
            holder = new BlankViewHolder(v);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        int viewType = getItemViewType(position);
        if(viewType == EXPENSE_VIEW){
            ExpenseViewHolder expenseViewHolder = (ExpenseViewHolder) holder;
            if(expenses != null){
                //Subtract the position by 1 since the first position is occupied by the tile view.
                //Data item begins at 2nd position.
                final Expense expense = expenses.get(position -1);
                expenseViewHolder.textViewCategory.setText(expense.type);
                expenseViewHolder.textViewDetails.setText(expense.details);
                expenseViewHolder.textViewAmount.setText(
                        (new DecimalFormat("#,###,##0.00")).format(expense.amount));
                expenseViewHolder.textViewDate.setText(
                        (new SimpleDateFormat("dd-MMM-yyyy")).format(expense.date));
            }else{
                expenseViewHolder.textViewDetails.setText("No expenses yet");
            }
        }else if(viewType == TITLE_VIEW){
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            onBindSpinnerListener.onBindSpinner(titleViewHolder.spinnerCategories);
        }
    }

    public void setExpenses(List<Expense> accounts){
        this.expenses = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(expenses != null)
            return expenses.size() + 2;
        else
            return 2;
    }

    @Override
    public int getItemViewType(int pos){
        if(pos == 0)
            return  TITLE_VIEW;
        else if (expenses != null && pos <= expenses.size())
            return EXPENSE_VIEW;
        else
            return BLANK_VIEW;
    }

    public static class BlankViewHolder extends RecyclerView.ViewHolder {

        public BlankViewHolder (View v){
            super(v);
        }
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewCategory;
        private final TextView textViewDetails;
        private final TextView textViewDate;
        private final TextView textViewAmount;

        private ExpenseViewHolder(View v){
            super(v);
            textViewCategory = itemView.findViewById(R.id.textView_type);
            textViewDetails = itemView.findViewById(R.id.textView_details);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewAmount = itemView.findViewById(R.id.textView_amount);
            itemView.setOnClickListener(view -> view.showContextMenu());
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        private final Spinner spinnerCategories;

        private TitleViewHolder(View v){
            super(v);
            spinnerCategories = itemView.findViewById(R.id.spinner_categories);
        }
    }

    public interface OnBindSpinnerListener {
        void onBindSpinner(Spinner spinner);
    }
}