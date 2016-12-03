package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Service;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 02/12/2016.
 */
public class ServicedGenDialog extends DialogFragment {

    private static final String TAG = ServicedGenDialog.class.getSimpleName();
    public static final String SERVICE_TAG = "serviced tag";

    @BindView(R.id.WorkPermitTextView)
    TextView mWorkPermitTextView;
    @BindView(R.id.GenSetSerialTextView)
    TextView mGenSetSerialTextView;
    @BindView(R.id.RHSTextView)
    TextView mRHSTextView;
    @BindView(R.id.OilPressureTextView)
    TextView mOilPressureTextView;
    @BindView(R.id.TempTextView)
    TextView mTempTextView;
    @BindView(R.id.L2LVoltageTextView)
    TextView mL2LVoltageTextView;
    @BindView(R.id.HZTextView)
    TextView mHZTextView;
    @BindView(R.id.BatteryChargeTextView)
    TextView mBatteryChargeTextView;
    @BindView(R.id.ActionsTextView)
    TextView mActionsTextView;
    @BindView(R.id.CommentsTextView)
    TextView mCommentsTextView;

    Unbinder unbinder;

    Service mService;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.serviced_gen_dialog, null);

        unbinder = ButterKnife.bind(this, view);
        mService = getArguments().getParcelable(SERVICE_TAG);

        return builder.setView(view)
                .setTitle("Service")
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
        mWorkPermitTextView.setText(getString(R.string.work_permit_text,
                mService.getWorkPermitNumber().isEmpty() ? "N/A" : mService.getWorkPermitNumber()));
        mCommentsTextView.setText(getString(R.string.comments_text,
                mService.getComments().isEmpty() ? "N/A" : mService.getComments()));
        mActionsTextView.setText(getString(R.string.actions_text,
                mService.getActions().isEmpty() ? "N/A" : mService.getActions()));
        mRHSTextView.setText(getString(R.string.rhs_text,
                mService.getRHS() == -1 ? "N/A" : String.valueOf(mService.getRHS())));
        mGenSetSerialTextView.setText(getString(R.string.gen_serial_text,
                mService.getGenerator().isEmpty() ? "N/A" : mService.getGenerator()));
        mOilPressureTextView.setText(getString(R.string.oil_pressure_text,
                mService.getOilPressure() == -1 ? "N/A" : String.valueOf(mService.getOilPressure())));
        mTempTextView.setText(getString(R.string.temp_text,
                mService.getTemp() == -1 ? "N/A" : String.valueOf(mService.getTemp())));
        mL2LVoltageTextView.setText(getString(R.string.l2l_voltage_text,
                mService.getL2lVoltage() == -1 ? "N/A" : String.valueOf(mService.getL2lVoltage())));
        mHZTextView.setText(getString(R.string.hz_text,
                mService.getHZ() == -1 ? "N/A" : String.valueOf(mService.getHZ())));
        mBatteryChargeTextView.setText(getString(R.string.battery_charge_text,
                mService.getBatteryCharge() == -1 ? "N/A" : String.valueOf(mService.getBatteryCharge())));
    }
}

