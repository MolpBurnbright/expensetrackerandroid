package com.kapirawan.financial_tracker.ui.account;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;

import java.text.DecimalFormat;
import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountViewHolder>{

    class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewExpense;
        private final TextView textViewFund;
        private final TextView textViewBalance;

        private AccountViewHolder(View v){
            super(v);
            textViewName = itemView.findViewById(R.id.textView_accountname);
            textViewExpense = itemView.findViewById(R.id.textView_expense);
            textViewFund = itemView.findViewById(R.id.textView_fund);
            textViewBalance = itemView.findViewById(R.id.textView_balance);
            itemView.setOnClickListener(view -> view.showContextMenu());
        }
    }

    private List<Account> accounts;
    private long selectedAccountId, selectedAccountDatasourceId;

    public AccountListAdapter() {
    }

    @Override
    public AccountListAdapter.AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_recyclerview_item_account, parent, false);
        return new AccountViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AccountListAdapter.AccountViewHolder holder, int position){
        if(accounts != null){
            final Account account = accounts.get(position);
            holder.textViewName.setText(account.name);
            //TODO: Populate expense, funds, and remaining budget with valid values.
            holder.textViewExpense.setText(new DecimalFormat("#,###,##0.00")
                    .format(0.00));
            holder.textViewFund.setText(new DecimalFormat("#,###,##0.00")
                    .format(0.00));
            holder.textViewBalance.setText(new DecimalFormat("#,###,##0.00")
                    .format(0.00));
            if(this.accounts.get(position)._id == selectedAccountId
                    && this.accounts.get(position).datasourceId == selectedAccountDatasourceId)
                holder.itemView.setBackgroundColor(Color.CYAN);
            else
                holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
    }

    public void setAccounts(List<Account> accounts){
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    public void setSelectedAccount(long accountId, long accountDatasourceId){
        this.selectedAccountId = accountId;
        this.selectedAccountDatasourceId = accountDatasourceId;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(accounts != null)
            return accounts.size();
        else
            return 0;
    }
}
