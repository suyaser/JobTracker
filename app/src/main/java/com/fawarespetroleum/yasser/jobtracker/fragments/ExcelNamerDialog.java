package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Install;
import com.fawarespetroleum.yasser.jobtracker.models.Service;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 21/10/2016.
 */
public class ExcelNamerDialog extends DialogFragment {

    private ExcelNamerDialog.OnDialogInteractionListener mListener;

    @BindView(R.id.ExcelEditText)
    TextInputLayout mExcelEditText;
    @BindView(R.id.ok_button)
    Button mOkButton;

    Unbinder unbinder;

    public ExcelNamerDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_excel_name, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mExcelEditText.getEditText().getText().toString().trim().matches("")) {
                    dismiss();
                }else{
                    mListener.onDialogInteraction(mExcelEditText.getEditText().getText().toString().trim());
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExcelNamerDialog.OnDialogInteractionListener) {
            mListener = (ExcelNamerDialog.OnDialogInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        unbinder.unbind();
    }

    public interface OnDialogInteractionListener {
        void onDialogInteraction(String excelFileName);
    }
}