package com.kapirawan.financial_tracker.ui.budget;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;

import java.text.SimpleDateFormat;

public class RemoveBudgetDialog extends DialogFragment {

    RemoveBudgetDialogViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the ViewModel
        this.viewModel = ViewModelProviders.of(this.getActivity()).get(RemoveBudgetDialogViewModel.class);
        View view = inflater.inflate(R.layout.budget_dialog_remove_budget, container, false);
        onCreateViewInitDate(view);
        onCreateViewInitType(view);
        onCreateViewInitAmount(view);
        onCreateViewInitAutocomplete(view);
        onCreateViewInitRemoveButton(view);
        view.findViewById(R.id.button_cancel).setOnClickListener(v -> this.getDialog().cancel());
        return view;
    }

    private void onCreateViewInitDate(View view){
        ((TextView) view.findViewById(R.id.textview_date))
                .setText(new SimpleDateFormat("dd-MMM-yyyy")
                        .format(this.viewModel.getDate()));
    }

    private void onCreateViewInitType(View view){
        ((TextView) view.findViewById(R.id.textview_type)).setText(viewModel.getCategory());
    }

    private void onCreateViewInitAmount(View view){
        TextView editText = view.findViewById(R.id.textview_amount);
        editText.setText(String.valueOf(viewModel.getAmount()));
    }

    private void onCreateViewInitAutocomplete(View view){
        TextView textView = view.findViewById(R.id.textview_details);
        textView.setText(viewModel.getDescription());
    }

    private void onCreateViewInitRemoveButton(View view){
        Button button = view.findViewById(R.id.button_remove);
        button.setOnClickListener(v -> {
            viewModel.removeBudget();
            getDialog().dismiss();
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}