package com.kapirawan.financial_tracker.expense;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpenseDialog extends DialogFragment {

    ViewModelAddExpense viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the ViewModel
        this.viewModel = ViewModelProviders.of(this.getActivity()).get(ViewModelAddExpense.class);
        this.viewModel.init(1, 0);
        View view = inflater.inflate(R.layout.dialog_add_expense, container, false);
        onCreateViewInitDate(view);
        onCreateViewInitType(view);
        return view;
    }

    private void onCreateViewInitDate(View view){
        view.findViewById(R.id.button_cancel).setOnClickListener(v -> this.getDialog().cancel());
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
                ((TextView) getDialog().findViewById(R.id.edittext_date))
                        .setText(new SimpleDateFormat("dd-MMM-yy")
                                .format(viewModel.getSelectedDate()));
            };
            //show the Date Picker
            new DatePickerDialog(this.getActivity(), listener, year, month, day).show();
        });
        ((TextView) view.findViewById(R.id.edittext_date))
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}