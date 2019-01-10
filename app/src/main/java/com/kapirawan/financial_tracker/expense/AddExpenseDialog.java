package com.kapirawan.financial_tracker.expense;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddExpenseDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    private final String SELECTED_DATE = "SELECTED_DATE";
    Date selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_expense, container, false);
        //Setup date picker
        view.findViewById(R.id.button_cancel).setOnClickListener(v -> this.getDialog().cancel());
        view.findViewById(R.id.button_datepicker).setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(this.selectedDate);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(this.getActivity(), this, year, month, day).show();
        });
        //set initial date selected to the current date
        ((TextView) view.findViewById(R.id.edittext_date))
                .setText(new SimpleDateFormat("dd-MMM-yyyy")
                        .format(this.selectedDate));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(SELECTED_DATE, selectedDate.getTime());
        super.onSaveInstanceState(outState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            selectedDate = new Date(savedInstanceState.getLong(SELECTED_DATE));
        }else{
            selectedDate = Calendar.getInstance().getTime();
        }
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            this.selectedDate = dateFormat.parse(String.valueOf(year)
                    + String.format("%02d", month + 1) + String.format("%02d", day));
        }catch (Exception e){
            //if there is an exception in formatting the date, set the selected date to the current
            //date
            this.selectedDate = Calendar.getInstance().getTime();
        }
        ((TextView) this.getDialog().findViewById(R.id.edittext_date))
                .setText(new SimpleDateFormat("dd-MMM-yy").format(this.selectedDate));
    }


}