package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 03/12/2016.
 */
public class TripedGenDialog extends DialogFragment {

    private static final String TAG = TripedGenDialog.class.getSimpleName();
    public static final String TRIP_TAG = "triped tag";

    @BindView(R.id.WorkPermitTextView)
    TextView mWorkPermitTextView;
    @BindView(R.id.GenSetSerialTextView)
    TextView mGenSetSerialTextView;
    @BindView(R.id.TripReasons)
    TextView mTripReasons;
    @BindView(R.id.ActionsTextView)
    TextView mActionsTextView;
    @BindView(R.id.CommentsTextView)
    TextView mCommentsTextView;
    @BindView(R.id.InformTimeTextView)
    TextView mInformTime;
    @BindView(R.id.StartTimeTextView)
    TextView mStartTime;

    Unbinder unbinder;

    Trip mTrip;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.triped_gen_dialog, null);

        unbinder = ButterKnife.bind(this, view);
        mTrip = getArguments().getParcelable(TRIP_TAG);

        return builder.setView(view)
                .setTitle("Trip")
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();

        populateViews();
    }

    private void populateViews() {
        DateFormat dataFormat = new DateFormat();

        mWorkPermitTextView.setText(getString(R.string.work_permit_text,
                mTrip.getWorkPermitNumber().isEmpty() ? "N/A" : mTrip.getWorkPermitNumber()));
        mGenSetSerialTextView.setText(getString(R.string.gen_serial_text,
                mTrip.getGenerator().isEmpty() ? "N/A" : mTrip.getGenerator()));
        mTripReasons.setText(getString(R.string.trip_reasons_text,
                mTrip.getTripReasons().isEmpty() ? "N/A" : mTrip.getTripReasons()));
        mActionsTextView.setText(getString(R.string.actions_text,
                mTrip.getActions().isEmpty() ? "N/A" : mTrip.getActions()));
        mCommentsTextView.setText(getString(R.string.comments_text,
                mTrip.getComments().isEmpty() ? "N/A" : mTrip.getComments()));
        mStartTime.setText(getString(R.string.start_time_text,
                mTrip.getStartTime() == null ? "N/A" : dataFormat.format("HH:mm", mTrip.getStartTime())));
        mInformTime.setText(getString(R.string.inform_time_text,
                mTrip.getInformTime() == null ? "N/A" : dataFormat.format("HH:mm", mTrip.getInformTime())));
    }
}

