package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.activities.InstallActivity;
import com.fawarespetroleum.yasser.jobtracker.activities.ServiceActivity;
import com.fawarespetroleum.yasser.jobtracker.models.Service;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class ServicedFragment extends Fragment {
    private static final String TAG = ServicedFragment.class.getSimpleName();

    @BindView(R.id.WorkPermitTextView)
    TextView mWorkPermitTextView;
    @BindView(R.id.SiteLocationTextView)
    TextView mSiteLocationTextView;
    @BindView(R.id.GenSetSerialTextView)
    TextView mGenSetSerialTextView;
    @BindView(R.id.RHSTextView)
    TextView mRHSTextView;
    @BindView(R.id.OilPressureTextView)
    TextView mOilPressureTextView;
    @BindView(R.id.TempTextView)
    TextView mTempTextView;
    @BindView(R.id.L2LVoltageTextView)
    TextView mL2LVoltageTextView;
    @BindView(R.id.HZTextView)
    TextView mHZTextView;
    @BindView(R.id.BatteryChargeTextView)
    TextView mBatteryChargeTextView;
    @BindView(R.id.ActionsTextView)
    TextView mActionsTextView;
    @BindView(R.id.CommentsTextView)
    TextView mCommentsTextView;

    Unbinder unbinder;
    private Service mService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_serviced, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        mService = getArguments().getParcelable(ServiceActivity.SERVICE_TAG);

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
        mWorkPermitTextView.setText(String.format(getString(R.string.work_permit_text,
                mService.getWorkPermitNumber().matches("") ? "N/A" : mService.getWorkPermitNumber())));
        mSiteLocationTextView.setText(String.format(getString(R.string.site_location_text,
                mService.getField().matches("") ? "N/A" : mService.getField() + "_" + mService.getSite())));
        mCommentsTextView.setText(String.format(getString(R.string.comments_text,
                mService.getComments().matches("") ? "N/A" : mService.getComments())));
        mActionsTextView.setText(String.format(getString(R.string.actions_text,
                mService.getActions().matches("") ? "N/A" : mService.getActions())));
        mRHSTextView.setText(String.format(getString(R.string.rhs_text,
                mService.getRHS() == -1 ? "N/A" : String.valueOf(mService.getRHS()))));
        mGenSetSerialTextView.setText(String.format(getString(R.string.gen_serial_text,
                mService.getGeneratorSerial().matches("") ? "N/A" : mService.getGeneratorSerial())));
        mOilPressureTextView.setText(String.format(getString(R.string.oil_pressure_text,
                mService.getOilPressure() == -1 ? "N/A" : String.valueOf(mService.getOilPressure()))));
        mTempTextView.setText(String.format(getString(R.string.temp_text,
                mService.getTemp() == -1 ? "N/A" : String.valueOf(mService.getTemp()))));
        mL2LVoltageTextView.setText(String.format(getString(R.string.l2l_voltage_text,
                mService.getL2lVoltage() == -1 ? "N/A" : String.valueOf(mService.getL2lVoltage()))));
        mHZTextView.setText(String.format(getString(R.string.hz_text,
                mService.getHZ() == -1 ? "N/A" : String.valueOf(mService.getHZ()))));
        mBatteryChargeTextView.setText(String.format(getString(R.string.battery_charge_text,
                mService.getBatteryCharge() == -1 ? "N/A" : String.valueOf(mService.getBatteryCharge()))));
    }

}
