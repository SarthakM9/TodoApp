package com.showcase.todoapp.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
{
    private DatePickerDialog.OnDateSetListener mListener;

    public DatePickerFragment()
    {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if (getActivity() instanceof DatePickerDialog.OnDateSetListener)
        {
            mListener = (DatePickerDialog.OnDateSetListener) getActivity();
        }

        DatePickerDialog dialog = new DatePickerDialog(getContext(), mListener, year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        return dialog;
    }
}
