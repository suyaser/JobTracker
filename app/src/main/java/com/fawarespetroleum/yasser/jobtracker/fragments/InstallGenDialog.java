package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Install;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 14/11/2016.
 */
public class InstallGenDialog extends DialogFragment {

    @BindView(R.id.fieldEditText)
    AutoCompleteTextView mFieldEditText;
    @BindView(R.id.WorkPermitEditText)
    TextInputLayout mWorkPermitEditText;
    @BindView(R.id.TankSerialEditText)
    TextInputLayout mTankSerialEditText;
    @BindView(R.id.SyncPanelEditText)
    TextInputLayout mSyncPanelEditText;
    @BindView(R.id.FireExtinguisherEditText)
    TextInputLayout mFireExtinguisherEditText;
    @BindView(R.id.FEExpireDateEditText)
    TextInputEditText mFEExpireDateEditText;
    @BindView(R.id.CommentsEditText)
    TextInputLayout mCommentsEditText;

    Unbinder unbinder;
    private OnAddGeneratorDialogListener mListener;
    private DatabaseReference mDatabase;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog expiryDatePickerDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_install_gen, null);

        unbinder = ButterKnife.bind(this, view);

        mFEExpireDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expiryDatePickerDialog.show();
            }
        });
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        setDateTimeField();

        return builder.setView(view)
                .setTitle("Install Generator")
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
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
                    mListener.onAddGenerator(getInstallObject(mFieldEditText.getText().toString()));
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
        if(mFieldEditText.getText().toString() == null || mFieldEditText.getText().toString().isEmpty()){
            mFieldEditText.setError("Field can't be empty");
            mFieldEditText.requestFocus();
            result = false;
        }
        return result;
    }


    @Override
    public void onResume() {
        super.onResume();
        final ArrayList<String> field = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("fields");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    field.add(snapshot.getKey());
                }
                Log.d("ddddd", String.valueOf(field.get(0)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, field);
        mFieldEditText.setAdapter(adapter);
    }

    private Install getInstallObject(String field) {
        Date date = new Date();
        String workPermitNO = mWorkPermitEditText.getEditText().getText().toString();
        String tankSerial = mTankSerialEditText.getEditText().getText().toString();
        String syncPanel = mSyncPanelEditText.getEditText().getText().toString();
        String fireExtinguisher = mFireExtinguisherEditText.getEditText().getText().toString();
        Date feExpiryDate = null;
        try {
            feExpiryDate = mFEExpireDateEditText.getText().toString().matches("") ? null :
                    dateFormatter.parse(mFEExpireDateEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String comments = mCommentsEditText.getEditText().getText().toString();
        return new Install(date,
                workPermitNO,
                field,
                tankSerial,
                syncPanel,
                fireExtinguisher,
                feExpiryDate,
                comments);
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        expiryDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mFEExpireDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddGeneratorDialogListener) {
            mListener = (OnAddGeneratorDialogListener) context;
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

    public interface OnAddGeneratorDialogListener {
        void onAddGenerator(Install install);
    }
}
