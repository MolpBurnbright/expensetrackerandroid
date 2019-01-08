package com.kapirawan.financial_tracker.summary;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kapirawan.financial_tracker.R;

public class FragmentSummary extends Fragment {
    ViewModelSummary viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_summary,container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_summary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        AdapterRecyclerViewSummary adapter = new AdapterRecyclerViewSummary();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(ViewModelSummary.class);
        viewModel.init(1, 0);
        viewModel.getSummary().observe(this, summaries  ->
            adapter.setSummaryDataset(summaries)
        );
        return rootView;
    }
}