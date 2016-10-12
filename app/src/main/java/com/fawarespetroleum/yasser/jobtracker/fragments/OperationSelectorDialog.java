package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fawarespetroleum.yasser.jobtracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 11/10/2016.
 */

public class OperationSelectorDialog extends DialogFragment {

    public static final int INSTALL_KEY = 1;
    public static final int SERVICE_KEY = 2;
    public static final int TRIP_KEY = 3;

    private OnDialogInteractionListener mListener;

    @BindView(R.id.InstallButton)
    Button mInstallButton;
    @BindView(R.id.ServiceButton)
    Button mServiceButton;
    @BindView(R.id.TripButton)
    Button mTripButton;

    Unbinder unbinder;

    public OperationSelectorDialog() {
    }

//    public static OperationSelectorDialog newInstance(String title) {
//        EditNameDialogFragment frag = new EditNameDialogFragment();
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        frag.setArguments(args);
//        return frag;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_operation_select, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        mInstallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDialogInteraction(INSTALL_KEY);
                dismiss();
            }
        });

        mServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDialogInteraction(SERVICE_KEY);
                dismiss();
            }
        });
        mTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDialogInteraction(TRIP_KEY);
                dismiss();
            }
        });
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
    }

    public interface OnDialogInteractionListener {
        void onDialogInteraction(int type);
    }
}
