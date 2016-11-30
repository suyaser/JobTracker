package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.fawarespetroleum.yasser.jobtracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 11/11/2016.
 */

public class PasswordChangeDialog extends DialogFragment {

    private OnDialogInteractionListener mListener;

    @BindView(R.id.PasswordEditText)
    TextInputLayout mPasswordEditText;
    @BindView(R.id.ok_button)
    Button mOkButton;

    Unbinder unbinder;

    public PasswordChangeDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_password_change, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPassword(mPasswordEditText.getEditText().getText().toString().trim())) {
                    mListener.onDialogInteraction(mPasswordEditText.getEditText().getText().toString().trim());
                    dismiss();
                }
            }
        });
    }

    private boolean checkPassword(String password) {
        if (password.isEmpty() || !isPasswordValid(password)) {
            mPasswordEditText.setError(getString(R.string.err_msg_password));
            requestFocus(mPasswordEditText);
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        return (password.length() >= 6);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogInteractionListener) {
            mListener = (OnDialogInteractionListener) context;
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
        void onDialogInteraction(String password);
    }
}
