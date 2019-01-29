package com.kapirawan.financial_tracker.fund;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class FundListAdapter extends RecyclerView.Adapter<FundListAdapter.FundViewHolder>{

    class FundViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewCategory;
        private final TextView textViewDetails;
        private final TextView textViewDate;
        private final TextView textViewAmount;

        private FundViewHolder(View v){
            super(v);
            textViewCategory = itemView.findViewById(R.id.textView_source);
            textViewDetails = itemView.findViewById(R.id.textView_details);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewAmount = itemView.findViewById(R.id.textView_amount);
            itemView.setOnClickListener(view -> view.showContextMenu());
        }
    }

    private List<Fund> funds;

    public FundListAdapter() {
    }

    @Override
    public FundListAdapter.FundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fund_recyclerview_item_fund, parent, false);
        return new FundViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FundListAdapter.FundViewHolder holder, int position){
        if(funds != null){
            final Fund fund = funds.get(position);
            holder.textViewCategory.setText(fund.type);
            holder.textViewDetails.setText(fund.details);
            holder.textViewAmount.setText(
                    (new DecimalFormat("#,###,##0.00")).format(fund.amount));
            holder.textViewDate.setText(
                    (new SimpleDateFormat("dd-MMM-yyyy")).format(fund.date));
        }else {
            holder.textViewDetails.setText("No accounts yet");
        }
    }

    public void setFunds(List<Fund> accounts){
        this.funds = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(funds != null)
            return funds.size();
        else
            return 0;
    }
}