package com.fawarespetroleum.yasser.jobtracker.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.activities.InstallActivity;
import com.fawarespetroleum.yasser.jobtracker.activities.ServiceActivity;
import com.fawarespetroleum.yasser.jobtracker.activities.TripActivity;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TripedFragment extends Fragment {
    private static final String TAG = TripedFragment.class.getSimpleName();

    @BindView(R.id.WorkPermitTextView)
    TextView mWorkPermitTextView;
    @BindView(R.id.SiteLocationTextView)
    TextView mSiteLocationTextView;
    @BindView(R.id.GenSetSerialTextView)
    TextView mGenSetSerialTextView;
    @BindView(R.id.TripReasons)
    TextView mTripReasons;
    @BindView(R.id.ActionsTextView)
    TextView mActionsTextView;
    @BindView(R.id.CommentsTextView)
    TextView mCommentsTextView;
    @BindView(R.id.InformTimeTextView)
    TextView mInformTime;
    @BindView(R.id.StartTimeTextView)
    TextView mStartTime;

    Unbinder unbinder;
    private Trip mTrip;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_triped, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        mTrip = getArguments().getParcelable(TripActivity.TRIP_TAG);

        populateViews();
    }

    private void populateViews() {
        DateFormat dataFormat = new DateFormat();

        mWorkPermitTextView.setText(mTrip.getWorkPermitNumber().matches("") ? "N/A" : mTrip.getWorkPermitNumber());
        mSiteLocationTextView.setText(mTrip.getField().matches("") ? "N/A" : mTrip.getField()+"_"+mTrip.getSite());
        mGenSetSerialTextView.setText(mTrip.getGeneratorSerial().matches("") ? "N/A" : mTrip.getGeneratorSerial());
        mTripReasons.setText(mTrip.getTripReasons().matches("") ? "N/A" : mTrip.getTripReasons());
        mActionsTextView.setText(mTrip.getActions().matches("") ? "N/A" : mTrip.getActions());
        mCommentsTextView.setText(mTrip.getComments().matches("") ? "N/A" : mTrip.getComments());
        mStartTime.setText(mTrip.getStartTime() == null ? "N/A" : dataFormat.format("dd-MM-yyyy HH:mm",mTrip.getStartTime()));
        mInformTime.setText(mTrip.getInformTime() == null ? "N/A" : dataFormat.format("dd-MM-yyyy HH:mm",mTrip.getInformTime()));
    }

}
