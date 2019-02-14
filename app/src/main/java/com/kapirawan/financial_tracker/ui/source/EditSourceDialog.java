package com.kapirawan.financial_tracker.ui.source;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.source.Source;

import java.util.List;

public class EditSourceDialog extends DialogFragment {

    EditSourceDialogViewModel viewModel;
    List<Source> sources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the ViewModel
        viewModel = ViewModelProviders.of(this.getActivity()).get(EditSourceDialogViewModel.class);
        viewModel.getCategories().observe(this, srs -> sources = srs);
        View view = inflater.inflate(R.layout.source_dialog_edit_source, container, false);
        onCreateViewInitName(view);
        onCreateViewInitUpdateButton(view);
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
                if (charSequence.length() > 0){
                    view.findViewById(R.id.button_update).setEnabled(true);
                }else
                    view.findViewById(R.id.button_update).setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        EditText name = view.findViewById(R.id.edittext_sourcename);
        name.addTextChangedListener(textWatcher);
        name.setText(viewModel.getSourceName());
    }

    private void onCreateViewInitUpdateButton(View view) {
        Button button = view.findViewById(R.id.button_update);
        button.setOnClickListener(v -> {
            String name = ((EditText)
                    view.findViewById(R.id.edittext_sourcename)).getText().toString().toUpperCase();
            boolean isNameInUse = false;
            for(Source srs: sources){
                if (srs.name.toUpperCase().equals(name))
                    isNameInUse = true;
            }
            if (isNameInUse)
                showError("Source Name is already in use");
            else{
                viewModel.updateSource(toTitleCase(name));
                getDialog().dismiss();
            }
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

    private String toTitleCase(String s) {
        //Characters that follow these delimeters will be capitalized
        final String ACTIONABLE_DELIMITERS = " '-/";

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : s.toCharArray()) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0);
        }
        return sb.toString();
    }
}