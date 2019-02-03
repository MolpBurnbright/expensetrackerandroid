package com.kapirawan.financial_tracker.fund;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditFundDialog extends DialogFragment {

    EditFundDialogViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the ViewModel
        this.viewModel = ViewModelProviders.of(this.getActivity()).get(EditFundDialogViewModel.class);
        View view = inflater.inflate(R.layout.fund_dialog_edit_fund, container, false);
        onCreateViewInitDate(view);
        onCreateViewInitType(view);
        onCreateViewInitAmount(view);
        onCreateViewInitAutocomplete(view);
        onCreateViewInitEditButton(view);
        view.findViewById(R.id.button_cancel).setOnClickListener(v -> this.getDialog().cancel());
        return view;
    }

    private void onCreateViewInitDate(View view){
        view.findViewById(R.id.button_datepicker).setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(this.viewModel.getDate());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            //listener of the DatePicker Dialog
            DatePickerDialog.OnDateSetListener listener = (picker, pYear, pMonth, pDay) -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                try {
                    viewModel.setDate(dateFormat.parse(String.valueOf(pYear)
                            + String.format("%02d", pMonth + 1) + String.format("%02d", pDay)));
                }catch (Exception e){
                    //if there is an exception in formatting the date, then
                    // set the selected date to the current date
                    viewModel.setDate(Calendar.getInstance().getTime());
                }
                ((TextView) getDialog().findViewById(R.id.textview_date))
                        .setText(new SimpleDateFormat("dd-MMM-yy")
                                .format(viewModel.getDate()));
            };
            //show the Date Picker
            new DatePickerDialog(this.getActivity(), listener, year, month, day).show();
        });
        ((TextView) view.findViewById(R.id.textview_date))
                .setText(new SimpleDateFormat("dd-MMM-yyyy")
                        .format(this.viewModel.getDate()));
    }

    private void onCreateViewInitType(View view){
        //Initialize the Type spinner
        this.viewModel.getSources().observe(this, sources -> {
            List<String> selectionList = new ArrayList<>();
            String fundSource = this.viewModel.getSource();
            int sourcePostion = -1;
            for(int i=0; i < sources.size(); i++){
                selectionList.add(sources.get(i).name);
                if(fundSource.equals(sources.get(i).name)){
                    sourcePostion = i;
                }
            }
            //If fund source does not exist in the current sources, then add it in the
            //selection list.
            if(sourcePostion == -1){
                sourcePostion = selectionList.size();
                selectionList.add(fundSource);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                    android.R.layout.simple_spinner_item, selectionList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = view.findViewById(R.id.spinner_source);
            spinner.setAdapter(adapter);
            spinner.setSelection(sourcePostion);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    viewModel.setSource(adapterView.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) { }
            });
        });
    }

    private void onCreateViewInitAmount(View view){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    view.findViewById(R.id.button_remove).setEnabled(true);
                }else
                    view.findViewById(R.id.button_remove).setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        EditText editText = view.findViewById(R.id.edittext_amount);
        editText.setText(String.valueOf(viewModel.getAmount()));
        editText.addTextChangedListener(textWatcher);
    }

    private void onCreateViewInitAutocomplete(View view){
        this.viewModel.getDetails().observe(this, details -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                    android.R.layout.simple_dropdown_item_1line, details);
            AutoCompleteTextView textView = view.findViewById(R.id.autocomplete_details);
            textView.setAdapter(adapter);
            textView.setText(viewModel.getDescription());
        });
    }

    private void onCreateViewInitEditButton(View view){
        Button button = view.findViewById(R.id.button_remove);
        button.setOnClickListener(v -> {
            try {
                double amount = Double.parseDouble(
                        ((EditText) view.findViewById(R.id.edittext_amount)).getText().toString());
                String description = ((AutoCompleteTextView)
                        view.findViewById(R.id.autocomplete_details)).getText().toString();
                viewModel.setAmount(amount);
                viewModel.setDescription(description);
                viewModel.updateFund();
                getDialog().dismiss();
            } catch (NumberFormatException e) {
                showError("Amount is invalid, kindly check.");
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
}
