package com.kapirawan.financial_tracker.expense;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>{

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewCategory;
        private final TextView textViewDetails;
        private final TextView textViewDate;
        private final TextView textViewAmount;

        private ExpenseViewHolder(View v){
            super(v);
            textViewCategory = itemView.findViewById(R.id.textView_category);
            textViewDetails = itemView.findViewById(R.id.textView_details);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewAmount = itemView.findViewById(R.id.textView_amount);
            itemView.setOnClickListener(view -> view.showContextMenu());
        }
    }

    private List<Expense> expenses;

    public ExpenseAdapter() {
    }

    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_expense, parent, false);
        return new ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExpenseAdapter.ExpenseViewHolder holder, int position){
        if(expenses != null){
            final Expense expense = expenses.get(position);
            holder.textViewCategory.setText(expense.type);
            holder.textViewDetails.setText(expense.details);
            holder.textViewAmount.setText(
                    (new DecimalFormat("#,###,##0.00")).format(expense.amount));
            holder.textViewDate.setText(
                    (new SimpleDateFormat("dd-MMM-yyyy")).format(expense.date));
        }else {
            holder.textViewDetails.setText("No accounts yet");
        }
    }

    public void setExpenses(List<Expense> accounts){
        this.expenses = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(expenses != null)
            return expenses.size();
        else
            return 0;
    }

    public interface OnItemClickListener{
        void onItemClick(Expense expense);
    }
}