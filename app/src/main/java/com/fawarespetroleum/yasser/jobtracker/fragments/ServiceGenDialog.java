package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Service;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 02/12/2016.
 */

public class ServiceGenDialog extends DialogFragment {
    private static final String TAG = ServiceGenDialog.class.getSimpleName();

    @BindView(R.id.WorkPermitEditText)
    TextInputLayout mWorkPermitEditText;
    @BindView(R.id.RHSEditText)
    TextInputLayout mRHSEditText;
    @BindView(R.id.OilPressureEditText)
    TextInputLayout mOilPressureEditText;
    @BindView(R.id.TempEditText)
    TextInputLayout mTempEditText;
    @BindView(R.id.L2LVoltageEditText)
    TextInputLayout mL2LVoltageEditText;
    @BindView(R.id.HZEditText)
    TextInputLayout mHZEditText;
    @BindView(R.id.BatteryChargeEditText)
    TextInputLayout mBatteryChargeEditText;
    @BindView(R.id.ActionsEditText)
    TextInputLayout mActionsEditText;
    @BindView(R.id.CommentsEditText)
    TextInputLayout mCommentsEditText;

    Unbinder unbinder;
    private OnServiceGeneratorDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_service_gen, null);

        unbinder = ButterKnife.bind(this, view);

        return builder.setView(view)
                .setTitle("Service")
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
                if(validate()){
                    mListener.onServiceGenerator(getServiceObject());
                    dismiss();
                }
            }
        });
    }

    private boolean validate() {
        Boolean result = true;
        if(mWorkPermitEditText.getEditText().getText().toString() == null || mWorkPermitEditText.getEditText().getText().toString().isEmpty()){
            mWorkPermitEditText.setError("Workpermit can't be empty");
            mWorkPermitEditText.requestFocus();
            result = false;
        }
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnServiceGeneratorDialogListener) {
            mListener = (OnServiceGeneratorDialogListener) context;
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

    public interface OnServiceGeneratorDialogListener {
        void onServiceGenerator(Service service);
    }

    private Service getServiceObject() {
        Date date = new Date();
        String workPermitNO = mWorkPermitEditText.getEditText().getText().toString();
        String comments = mCommentsEditText.getEditText().getText().toString();
        String actions = mActionsEditText.getEditText().getText().toString();
        int RHS = mRHSEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mRHSEditText.getEditText().getText().toString());
        int oilPressure = mOilPressureEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mOilPressureEditText.getEditText().getText().toString());
        int temp = mTempEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mTempEditText.getEditText().getText().toString());
        int L2LVoltage = mL2LVoltageEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mL2LVoltageEditText.getEditText().getText().toString());
        int HZ = mHZEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mHZEditText.getEditText().getText().toString());
        int batteryCharge =  mBatteryChargeEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mBatteryChargeEditText.getEditText().getText().toString());
        return new Service(date,
                workPermitNO,
                actions,
                comments,
                RHS,
                oilPressure,
                temp,
                L2LVoltage,
                HZ,
                batteryCharge);
    }

}