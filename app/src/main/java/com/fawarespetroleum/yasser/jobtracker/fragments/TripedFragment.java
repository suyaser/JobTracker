package com.fawarespetroleum.yasser.jobtracker.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
                mTrip.getWorkPermitNumber().matches("") ? "N/A" : mTrip.getWorkPermitNumber())));
        mSiteLocationTextView.setText(String.format(getString(R.string.site_location_text,
                mTrip.getField().matches("") ? "N/A" : mTrip.getField() + "_" + mTrip.getSite())));
        mGenSetSerialTextView.setText(String.format(getString(R.string.gen_serial_text,
                mTrip.getGeneratorSerial().matches("") ? "N/A" : mTrip.getGeneratorSerial())));
        mTripReasons.setText(String.format(getString(R.string.trip_reasons_text,
                mTrip.getTripReasons().matches("") ? "N/A" : mTrip.getTripReasons())));
        mActionsTextView.setText(String.format(getString(R.string.actions_text,
                mTrip.getActions().matches("") ? "N/A" : mTrip.getActions())));
        mCommentsTextView.setText(String.format(getString(R.string.comments_text,
                mTrip.getComments().matches("") ? "N/A" : mTrip.getComments())));
        mStartTime.setText(String.format(getString(R.string.start_time_text,
                mTrip.getStartTime() == null ? "N/A" : DateFormat.format("HH:mm", mTrip.getStartTime()))));
        mInformTime.setText(String.format(getString(R.string.inform_time_text,
                mTrip.getInformTime() == null ? "N/A" : DateFormat.format("HH:mm", mTrip.getInformTime()))));
    }

}
