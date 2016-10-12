package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.activities.InstallActivity;
import com.fawarespetroleum.yasser.jobtracker.models.Install;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InstalledFragment extends Fragment {

    private static final String TAG = InstalledFragment.class.getSimpleName();

    @BindView(R.id.WorkPermitTextView)
    TextView mWorkPermitTextView;
    @BindView(R.id.SiteLocationTextView)
    TextView mSiteLocationTextView;
    @BindView(R.id.ContractorTextView)
    TextView mContractorTextView;
    @BindView(R.id.GenSetSerialTextView)
    TextView mGenSetSerialTextView;
    @BindView(R.id.GenSetSizeTextView)
    TextView mGenSetSizeTextView;
    @BindView(R.id.TankSerialTextView)
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_installed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        mInstall = getArguments().getParcelable(InstallActivity.INSTALL_TAG);

        populateViews();
    }

    private void populateViews() {
        DateFormat dataFormat = new DateFormat();

        mWorkPermitTextView.setText(mInstall.getWorkPermitNumber().matches("") ? "N/A" : mInstall.getWorkPermitNumber());
        mSiteLocationTextView.setText(mInstall.getField().matches("") ? "N/A" : mInstall.getField()+"_"+mInstall.getSite());
        mContractorTextView.setText(mInstall.getContractor().matches("") ? "N/A" : mInstall.getContractor());
        mGenSetSerialTextView.setText(mInstall.getGeneratorSerial().matches("") ? "N/A" : mInstall.getGeneratorSerial());
        mGenSetSizeTextView.setText(mInstall.getGeneratorSize() == -1 ? "N/A" : String.valueOf(mInstall.getGeneratorSize()+" mVA"));
        mTankSerialTextView.setText(mInstall.getTankSerial().matches("") ? "N/A" : mInstall.getTankSerial());
        mSyncPanelTextView.setText(mInstall.getSyncPanel().matches("") ? "N/A" : mInstall.getSyncPanel());
        mFireExtinguisherTextView.setText(mInstall.getFireExtinguisher().matches("") ? "N/A" : mInstall.getFireExtinguisher());
        mFEExpireDate.setText(mInstall.getFEExpiryDate() == null ? "N/A" : dataFormat.format("dd-MM-yyyy",mInstall.getFEExpiryDate()));
        mCommentsTextView.setText(mInstall.getComments().matches("") ? "N/A" : mInstall.getComments());
    }
}
