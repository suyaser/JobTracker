package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 12/11/2016.
 */

public class AddSiteDialog extends DialogFragment {

    @BindView(R.id.addSiteEditText)
    EditText mAddSiteEditText;
    @BindView(R.id.addContractorEditText)
    EditText mAddContractorEditText;

    Unbinder unbinder;
    private OnAddSiteDialogListener mListener;

    Pattern pattern;
    Matcher matcher;
    DatabaseReference myRef;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_site, null);

        unbinder = ButterKnife.bind(this, view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        return builder.setView(view)
                .setTitle("Add site_field")
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (validateSite() && validateContractor()) {
                                    mListener.onAddSite(mAddSiteEditText.getText().toString(), mAddContractorEditText.getText().toString());
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

    private boolean validateContractor() {
        if (mAddContractorEditText.getText().toString().matches("")) {
            Toast.makeText(getActivity(), "Contractor can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateSite() {
        pattern = Pattern.compile("([A-Z0-9])+(_)([A-Z0-9]+)");
        matcher = pattern.matcher(mAddSiteEditText.getText().toString());
        if (matcher.matches()) {
            return true;
        }
        Toast.makeText(getActivity(), "input must match site_field ex: 'RA_559'", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddSiteDialogListener) {
            mListener = (OnAddSiteDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddGeneratorDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        unbinder.unbind();

    }

    public interface OnAddSiteDialogListener {
        void onAddSite(String site, String contractor);
    }
}
