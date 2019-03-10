package com.kapirawan.financial_tracker.ui.fund;

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

public class FundListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int FUND_VIEW = 0;
    private static final int TITLE_VIEW = 1;
    private static final int BLANK_VIEW = 2;

    private List<Fund> funds;

    public FundListAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == FUND_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fund_recyclerview_item_fund, parent, false);
            holder = new FundViewHolder(v);
        }else if (viewType == TITLE_VIEW){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fund_recyclerview_title, parent, false);
            holder = new FundViewHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fund_recyclerview_blank, parent, false);
            holder = new FundViewHolder(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        int viewType = getItemViewType(position);
        if(viewType == FUND_VIEW) {
            FundViewHolder fundViewHolder = (FundViewHolder) holder;
            if (funds != null) {
                //Subtract the position by 1 since the first position is occupied by the tile view.
                //Data item begins at 2nd position.
                final Fund fund = funds.get(position - 1);
                fundViewHolder.textViewCategory.setText(fund.type);
                fundViewHolder.textViewDetails.setText(fund.details);
                fundViewHolder.textViewAmount.setText(
                        (new DecimalFormat("#,###,##0.00")).format(fund.amount));
                fundViewHolder.textViewDate.setText(
                        (new SimpleDateFormat("dd-MMM-yyyy")).format(fund.date));
            } else {
                fundViewHolder.textViewDetails.setText("No accounts yet");
            }
        }
    }

    public void setFunds(List<Fund> accounts){
        this.funds = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(funds != null)
            return funds.size() + 2;
        else
            return 2;
    }

    @Override
    public int getItemViewType(int pos){
        if(pos == 0)
            return  TITLE_VIEW;
        else if (funds != null && pos <= funds.size())
            return FUND_VIEW;
        else
            return BLANK_VIEW;
    }

    public static class BlankViewHolder extends RecyclerView.ViewHolder {

        private BlankViewHolder (View v){
            super(v);
        }
    }

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

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        private TitleViewHolder(View v){
            super(v);
        }
    }

}