package com.kapirawan.financial_tracker.ui.source;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;

public class RemoveSourceDialog extends DialogFragment {

    RemoveSourceDialogViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the ViewModel
        viewModel = ViewModelProviders.of(this.getActivity()).get(RemoveSourceDialogViewModel.class);
        View view = inflater.inflate(R.layout.source_dialog_remove_source, container, false);
        onCreateViewInitName(view);
        onCreateViewInitRemoveButton(view);
        view.findViewById(R.id.button_cancel).setOnClickListener(v -> this.getDialog().cancel());
        return view;
    }

    private void onCreateViewInitName(View view){
        ((TextView) view.findViewById(R.id.textview_sourcename)).setText(viewModel.getSourceName());
    }

    private void onCreateViewInitRemoveButton(View view) {
        Button button = view.findViewById(R.id.button_remove);
        button.setOnClickListener(v -> {
            viewModel.removeSource();
            getDialog().dismiss();
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void showError(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss()
        );
        alertDialog.show();
    }
}