package com.kapirawan.financial_tracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.database.roomdatabase.expense.Expense;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>{

    private final LayoutInflater inflater;
    private final ExpenseAdapter.OnItemClickListener listener;
    private List<Expense> expenses;

    public ExpenseAdapter(Context context, ExpenseAdapter.OnItemClickListener listen) {
        inflater = LayoutInflater.from(context);
        listener = listen;
    }

    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item_expense, parent, false);
        return new ExpenseAdapter.ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpenseAdapter.ExpenseViewHolder holder, int position){
        if(expenses != null){
            final Expense expense = expenses.get(position);
            holder.textViewCategory.setText(expense.type);
            holder.textViewDetails.setText(expense.details);
            holder.textViewAmount.setText((new DecimalFormat("#,###,##0.00")).format(expense.amount));
            holder.textViewDate.setText((new SimpleDateFormat("dd-MMM-yyyy")).format(expense.date));
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v) {
                    listener.onItemClick(expense);
                }
            });
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

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private final TextView textViewCategory;
        private final TextView textViewDetails;
        private final TextView textViewDate;
        private final TextView textViewAmount;
        private final ImageView imageViewIcon;

        private ExpenseViewHolder(View itemView){
            super(itemView);
            this.itemView = itemView;
            textViewCategory = itemView.findViewById(R.id.textView_category);
            textViewDetails = itemView.findViewById(R.id.textView_details);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewAmount = itemView.findViewById(R.id.textView_amount);
            imageViewIcon = itemView.findViewById(R.id.imageView_icon);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Expense expense);
    }
}