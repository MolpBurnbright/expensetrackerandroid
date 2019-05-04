package com.kapirawan.financial_tracker.ui.budget;

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
import com.kapirawan.financial_tracker.activities.ActivityMainViewModel;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddBudgetDialog extends DialogFragment {

    AddBudgetDialogViewModel viewModel;
    double totalBudget, totalFund, availableAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.budget_dialog_add_budget, container, false);
        //Create the ViewModel
        long userId = ViewModelProviders.of(this.getActivity()).get(ActivityMainViewModel.class).getUser()._id;
        long datasourceId = ViewModelProviders.of(this.getActivity()).get(ActivityMainViewModel.class).getDatasource()._id;
        viewModel = ViewModelProviders.of(this.getActivity()).get(AddBudgetDialogViewModel.class);
        viewModel.initUserId(userId);
        viewModel.initDatasourceId(datasourceId);
        viewModel.getSelectedAccount().observe(this, selectedAccount -> {
            String[] parsedValues = selectedAccount.value.split(",");
            long accountID = Long.parseLong(parsedValues[0]);
            long accounDatasourceId = Long.parseLong(parsedValues[1]);
            viewModel.init(accountID, accounDatasourceId);
            onCreateViewInitDate(view);
            onCreateViewInitType(view);
            onCreateViewInitAmount(view);
            onCreateViewInitAutocomplete(view);
            onCreateViewInitAddButton(view);
            viewModel.getTotalBudget().observe(this, budget -> {
                totalBudget = budget;
                availableAmount = totalFund - totalBudget;
                onCreateUpdateAvailableAmount(view);
            });
            viewModel.getTotalFund().observe(this, fund -> {
                totalFund = fund;
                availableAmount = totalFund - totalBudget;
                onCreateUpdateAvailableAmount(view);
            });
            view.findViewById(R.id.button_cancel).setOnClickListener(v -> this.getDialog().cancel());
        });
        return view;
    }

    private void onCreateViewInitDate(View view){
        view.findViewById(R.id.button_datepicker).setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(this.viewModel.getSelectedDate());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            //listener of the DatePicker Dialog
            DatePickerDialog.OnDateSetListener listener = (picker, pYear, pMonth, pDay) -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                try {
                    viewModel.setSelectedDate(dateFormat.parse(String.valueOf(pYear)
                            + String.format("%02d", pMonth + 1) + String.format("%02d", pDay)));
                }catch (Exception e){
                    //if there is an exception in formatting the date, then
                    // set the selected date to the current date
                    viewModel.setSelectedDate(Calendar.getInstance().getTime());
                }
                ((TextView) getDialog().findViewById(R.id.textview_date))
                        .setText(new SimpleDateFormat("dd-MMM-yy")
                                .format(viewModel.getSelectedDate()));
            };
            //show the Date Picker
            new DatePickerDialog(this.getActivity(), listener, year, month, day).show();
        });
        ((TextView) view.findViewById(R.id.textview_date))
                .setText(new SimpleDateFormat("dd-MMM-yyyy")
                        .format(this.viewModel.getSelectedDate()));
    }

    private void onCreateViewInitType(View view){
        //Initialize the Type spinner
        this.viewModel.getCategories().observe(this, categories -> {
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(this.getActivity(),
                    android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = view.findViewById(R.id.spinner_type);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    viewModel.setSelectedCategoryPosition(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) { }
            });
            spinner.setSelection(viewModel.getSelectedCategoryPosition());
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
                    double amount = Double.parseDouble(
                            ((EditText) view.findViewById(R.id.edittext_amount)).getText().toString());
                    if(amount <= availableAmount)
                        view.findViewById(R.id.button_update).setEnabled(true);
                    else
                        view.findViewById(R.id.button_update).setEnabled(false);
                }else
                    view.findViewById(R.id.button_update).setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        ((EditText)view.findViewById(R.id.edittext_amount))
                .addTextChangedListener(textWatcher);
    }

    private void onCreateViewInitAutocomplete(View view){
        this.viewModel.getDetails().observe(this, details -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                    android.R.layout.simple_dropdown_item_1line, details);
            ((AutoCompleteTextView) view.findViewById(R.id.autocomplete_details))
                    .setAdapter(adapter);
        });
    }

    private void onCreateViewInitAddButton(View view){
        Button button = view.findViewById(R.id.button_update);
        button.setOnClickListener(v -> {
            try {
                double amount = Double.parseDouble(
                        ((EditText) view.findViewById(R.id.edittext_amount)).getText().toString());
                String description = ((AutoCompleteTextView)
                        view.findViewById(R.id.autocomplete_details)).getText().toString();
                viewModel.setAmount(amount);
                viewModel.setDescription(description);
                viewModel.addBudget();
                getDialog().dismiss();
            } catch (NumberFormatException e) {
                showError("Amount is invalid, kindly check.");
            }
        });
        button.setEnabled(false);
    }

    private void onCreateUpdateAvailableAmount(View view){
        TextView textView = view.findViewById(R.id.textView_availableAmount);
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        textView.setText("Available Amount to Allocate : " + formatter.format(availableAmount));
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