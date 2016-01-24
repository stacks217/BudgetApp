package edu.uci.stacks.easybudget.activity.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        Bundle args = getArguments();
        int year = args.getInt("year");
        int month = args.getInt("month");
        int day = args.getInt("day");

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)  getActivity(), year, month, day);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            DatePickerDialog.OnDateSetListener pickerActivity = (DatePickerDialog.OnDateSetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DatePickerDialog.OnDateSetListener");
        }
    }
}