package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 13/11/2016.
 */
public class AddGeneratorDialog extends DialogFragment {

    @BindView(R.id.fieldEditText)
    EditText mGeneratorSerialEditText;
    @BindView(R.id.generatorSizeEditText)
    EditText mGeneratorSizeEditText;

    Unbinder unbinder;
    private OnAddGeneratorDialogListener mListener;

    DatabaseReference myRef;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_generator, null);

        unbinder = ButterKnife.bind(this, view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        return builder.setView(view)
                .setTitle("new Generator")
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (validateSerial() && validateSize()) {
                                    mListener.onAddGenerator(mGeneratorSerialEditText.getText().toString(), Integer.parseInt(mGeneratorSizeEditText.getText().toString()));
                                }
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

    private boolean validateSize() {
        if (mGeneratorSizeEditText.getText().toString().matches("")) {
            Toast.makeText(getActivity(), "Size can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateSerial() {
        if (mGeneratorSizeEditText.getText().toString().matches("")) {
            Toast.makeText(getActivity(), "Serial can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
        void onAddGenerator(String serial, int size);
    }
}
