package com.kapirawan.financial_tracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.entity.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder>{

    private final LayoutInflater inflater;
    private final OnItemClickListener listener;
    private List<Account> accounts;

    public AccountAdapter(Context context, OnItemClickListener listen) {
        inflater = LayoutInflater.from(context);
        listener = listen;
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item_account, parent, false);
        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position){
        if(accounts != null){
            final Account current = accounts.get(position);
            holder.accountItemView.setText(current.name);
            holder.accountItemView.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v) {
                    listener.onItemClick(current);
                }
            });
        }else {
            holder.accountItemView.setText("No accounts yet");
        }
    }

    public void setAccounts(List<Account> accounts){
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(accounts != null)
            return accounts.size();
        else
            return 0;
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView accountItemView;

        private AccountViewHolder(View itemView){
            super(itemView);
            accountItemView = itemView.findViewById(R.id.textView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Account account);
    }
}
