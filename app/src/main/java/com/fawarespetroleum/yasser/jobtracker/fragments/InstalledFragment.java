package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.MenuBar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        populateViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                //TODO edit opertaion
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_operation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void populateViews() {
        DateFormat dataFormat = new DateFormat();

        mWorkPermitTextView.setText(String.format(getString(R.string.work_permit_text,
                mInstall.getWorkPermitNumber().matches("") ? "N/A" : mInstall.getWorkPermitNumber())));
        mSiteLocationTextView.setText(String.format(getString(R.string.site_location_text,
                mInstall.getField().matches("") ? "N/A" : mInstall.getField() + "_" + mInstall.getSite())));
        mContractorTextView.setText(String.format(getString(R.string.contractor_text),
                mInstall.getContractor().matches("") ? "N/A" : mInstall.getContractor()));
        mGenSetSerialTextView.setText(String.format(getString(R.string.gen_serial_text,
                mInstall.getGeneratorSerial().matches("") ? "N/A" : mInstall.getGeneratorSerial())));
        mGenSetSizeTextView.setText(String.format(getString(R.string.gen_size_text,
                mInstall.getGeneratorSize() == -1 ? "N/A" : String.valueOf(mInstall.getGeneratorSize() + " kVA"))));
        mTankSerialTextView.setText(String.format(getString(R.string.tank_serial_text,
                mInstall.getTankSerial().matches("") ? "N/A" : mInstall.getTankSerial())));
        mSyncPanelTextView.setText(String.format(getString(R.string.sync_panel_text,
                mInstall.getSyncPanel().matches("") ? "N/A" : mInstall.getSyncPanel())));
        mFireExtinguisherTextView.setText(String.format(getString(R.string.fire_extinguisher_text,
                mInstall.getFireExtinguisher().matches("") ? "N/A" : mInstall.getFireExtinguisher())));
        mFEExpireDate.setText(String.format(getString(R.string.fe_expiry_text,
                mInstall.getFEExpiryDate() == null ? "N/A" : DateFormat.format("dd-MM-yyyy", mInstall.getFEExpiryDate()))));
        mCommentsTextView.setText(String.format(getString(R.string.comments_text,
                mInstall.getComments().matches("") ? "N/A" : mInstall.getComments())));
    }
}
