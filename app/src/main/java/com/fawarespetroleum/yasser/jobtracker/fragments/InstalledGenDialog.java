package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Install;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 02/12/2016.
 */

public class InstalledGenDialog extends DialogFragment {

    private static final String TAG = InstalledGenDialog.class.getSimpleName();
    public static final String INSTALL_TAG = "installed tag";

    @BindView(R.id.WorkPermitTextView)
    TextView mWorkPermitTextView;
    @BindView(R.id.GenSetSerialTextView)
    TextView mGenSetSerialTextView;
    @BindView(R.id.tankSerialTextView)
    TextView mTankSerialTextView;
    @BindView(R.id.SyncPanelTextView)
    TextView mSyncPanelTextView;
    @BindView(R.id.FireExtinguisherTextView)
    TextView mFireExtinguisherTextView;
    @BindView(R.id.FEExpireDate)
    TextView mFEExpireDate;
    @BindView(R.id.CommentsTextView)
    TextView mCommentsTextView;

    Unbinder unbinder;

    Install mInstall;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.installed_gen_dialog, null);

        unbinder = ButterKnife.bind(this, view);
        mInstall = getArguments().getParcelable(InstalledGenDialog.INSTALL_TAG);

        return builder.setView(view)
                .setTitle("Install")
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
                mInstall.getWorkPermitNumber().isEmpty() ? "N/A" : mInstall.getWorkPermitNumber()));
        mGenSetSerialTextView.setText(getString(R.string.gen_serial_text,
                mInstall.getGenerator().isEmpty() ? "N/A" : mInstall.getGenerator()));
        mTankSerialTextView.setText(getString(R.string.tank_serial_text,
                mInstall.getTankSerial().isEmpty() ? "N/A" : mInstall.getTankSerial()));
        mSyncPanelTextView.setText(getString(R.string.sync_panel_text,
                mInstall.getSyncPanel().isEmpty() ? "N/A" : mInstall.getSyncPanel()));
        mFireExtinguisherTextView.setText(getString(R.string.fire_extinguisher_text,
                mInstall.getFireExtinguisher().isEmpty() ? "N/A" : mInstall.getFireExtinguisher()));
        mFEExpireDate.setText(getString(R.string.fe_expiry_text,
                mInstall.getFEExpiryDate() == null ? "N/A" : dataFormat.format("dd-MM-yyyy", mInstall.getFEExpiryDate())));
        mCommentsTextView.setText(getString(R.string.comments_text,
                mInstall.getComments().isEmpty() ? "N/A" : mInstall.getComments()));
    }
}

