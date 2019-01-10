package com.kapirawan.financial_tracker.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DatePickerDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    Date initDate;
    Date selectedDate;
    DateSetListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        if (initDate != null)
            cal.setTime(this.initDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            this.selectedDate = dateFormat.parse(String.valueOf(year)
                    + String.format("%02d", month) + String.format("%02d", day));
            this.listener.dateSet(this.selectedDate);
        }catch (Exception e){
            this.selectedDate = null;
        }
    }

    public interface DateSetListener {
        void dateSet(Date date);
    }
}