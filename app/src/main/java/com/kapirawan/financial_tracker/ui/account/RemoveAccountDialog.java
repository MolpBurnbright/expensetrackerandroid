package com.kapirawan.financial_tracker.ui.account;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;

public class RemoveAccountDialog extends DialogFragment {

    RemoveAccountDialogViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the ViewModel
        viewModel = ViewModelProviders.of(this.getActivity()).get(RemoveAccountDialogViewModel.class);
        View view = inflater.inflate(R.layout.account_dialog_remove_accoount, container, false);
        onCreateViewInitName(view);
        onCreateViewInitRemoveButton(view);
        view.findViewById(R.id.button_cancel).setOnClickListener(v -> this.getDialog().cancel());
        return view;
    }

    private void onCreateViewInitName(View view){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Debug", "charSequence: " + charSequence.toString());
                Log.d("Debug", "Account Name: " + viewModel.getAccountName());
                if (charSequence.toString().equals(viewModel.getAccountName())){
                    view.findViewById(R.id.button_remove).setEnabled(true);
                }else
                    view.findViewById(R.id.button_remove).setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        ((TextView) view.findViewById(R.id.textView_accountnamedisp)).setText(viewModel.getAccountName());
        ((EditText) view.findViewById(R.id.edittext_categoryname)).addTextChangedListener(textWatcher);
    }

    private void onCreateViewInitRemoveButton(View view) {
        Button button = view.findViewById(R.id.button_remove);
        button.setOnClickListener(v -> {
            viewModel.removeAccount();
            getDialog().dismiss();
        });
        button.setEnabled(false);
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