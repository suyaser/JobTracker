package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 02/12/2016.
 */

public class TripGenDialog extends DialogFragment {
    private static final String TAG = TripGenDialog.class.getSimpleName();

    @BindView(R.id.WorkPermitEditText)
    TextInputLayout mWorkPermitEditText;
    @BindView(R.id.TripReasons)
    TextInputLayout mTripReasons;
    @BindView(R.id.ActionsEditText)
    TextInputLayout mActionsEditText;
    @BindView(R.id.CommentsEditText)
    TextInputLayout mCommentsEditText;
    @BindView(R.id.InformEditText)
    TextInputEditText mInformTimeEditText;
    @BindView(R.id.StartTimeEditText)
    TextInputEditText mStartTimeEditText;

    private TimePickerDialog informTimePickerDialog;
    private TimePickerDialog startTimePickerDialog;
    private SimpleDateFormat dateFormatter;

    Unbinder unbinder;
    private OnTripGeneratorDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_trip_gen, null);

        unbinder = ButterKnife.bind(this, view);

        mInformTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informTimePickerDialog.show();
            }
        });
        mStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimePickerDialog.show();
            }
        });
        dateFormatter = new SimpleDateFormat("HH:mm");
        setDateTimeField();

        return builder.setView(view)
                .setTitle("Trip")
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                )
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        Button button = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    mListener.onTripGenerator(getTripObject());
                    dismiss();
                }
            }
        });
    }

    private boolean validate() {
        Boolean result = true;
        if (mWorkPermitEditText.getEditText().getText().toString() == null || mWorkPermitEditText.getEditText().getText().toString().isEmpty()) {
            mWorkPermitEditText.setError("Workpermit can't be empty");
            mWorkPermitEditText.requestFocus();
            result = false;
        }
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTripGeneratorDialogListener) {
            mListener = (OnTripGeneratorDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTripGeneratorDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        unbinder.unbind();

    }

    public interface OnTripGeneratorDialogListener {
        void onTripGenerator(Trip trip);
    }

    private Trip getTripObject() {
        Date date = new Date();
        String workPermitNO = mWorkPermitEditText.getEditText().getText().toString();
        String comments = mCommentsEditText.getEditText().getText().toString();
        String actions = mActionsEditText.getEditText().getText().toString();
        String tripReasons = mTripReasons.getEditText().getText().toString();
        Date informTime = null;
        try {
            informTime = mInformTimeEditText.getText().toString().matches("") ? null :
                    dateFormatter.parse(mInformTimeEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date startTime = null;
        try {
            startTime = mStartTimeEditText.getText().toString().matches("") ? null :
                    dateFormatter.parse(mStartTimeEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Trip(date,
                workPermitNO,
                actions,
                comments,
                tripReasons,
                informTime,
                startTime);
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        informTimePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR, hour);
                newDate.set(Calendar.MINUTE, minute);
                mInformTimeEditText.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);
        startTimePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR, hour);
                newDate.set(Calendar.MINUTE, minute);
                mStartTimeEditText.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);
    }

}