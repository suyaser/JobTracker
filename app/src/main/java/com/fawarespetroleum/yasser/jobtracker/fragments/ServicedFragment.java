package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.support.annotation.Nullable;
import android.text.format.DateFormat;
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

        populateViews();
    }

    private void populateViews() {
        mWorkPermitTextView.setText(mService.getWorkPermitNumber().matches("") ? "N/A" : mService.getWorkPermitNumber());
        mSiteLocationTextView.setText(mService.getField().matches("") ? "N/A" : mService.getField()+"_"+mService.getSite());
        mCommentsTextView.setText(mService.getComments().matches("") ? "N/A" : mService.getComments());
        mActionsTextView.setText(mService.getActions().matches("") ? "N/A" : mService.getActions());
        mRHSTextView.setText(mService.getRHS() == -1 ? "N/A" : String.valueOf(mService.getRHS()));
        mGenSetSerialTextView.setText(mService.getGeneratorSerial().matches("")? "N/A" : mService.getGeneratorSerial());
        mOilPressureTextView.setText(mService.getOilPressure() == -1 ? "N/A" : String.valueOf(mService.getOilPressure()));
        mTempTextView.setText(mService.getTemp() == -1 ? "N/A" : String.valueOf(mService.getTemp()));
        mL2LVoltageTextView.setText(mService.getL2lVoltage() == -1 ? "N/A" : String.valueOf(mService.getL2lVoltage()));
        mHZTextView.setText(mService.getHZ() == -1 ? "N/A" : String.valueOf(mService.getHZ()));
        mBatteryChargeTextView.setText(mService.getBatteryCharge() == -1 ? "N/A" : String.valueOf(mService.getBatteryCharge()));
    }

}
