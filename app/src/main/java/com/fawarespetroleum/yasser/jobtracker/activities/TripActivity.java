package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Service;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TripActivity extends AppCompatActivity {
    private static final String TAG = ServiceActivity.class.getSimpleName();
    public static final String TRIP_KEY = "Trip key";

    @BindView(R.id.WorkPermitEditText)
    TextInputLayout mWorkPermitEditText;
    @BindView(R.id.FieldEditText)
    TextInputLayout mFieldEditText;
    @BindView(R.id.SiteEditText)
    TextInputLayout mSiteEditText;
    @BindView(R.id.GenSetSerialEditText)
    TextInputLayout mGenSetSerialEditText;
    @BindView(R.id.TripReasons)
    TextInputLayout mTripReasons;
    @BindView(R.id.ActionsEditText)
    TextInputLayout mActionsEditText;
    @BindView(R.id.CommentsEditText)
    TextInputLayout mCommentsEditText;
    @BindView(R.id.InformTime)
    DatePicker mInformTime;
    @BindView(R.id.StartTime)
    DatePicker mStartTime;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        unbinder = ButterKnife.bind(this);
    }

    public void SumbitData(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(TRIP_KEY, getTripObject());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private Trip getTripObject() {
        Date date = new Date();
        String workPermitNO = mWorkPermitEditText.getEditText().getText().toString();
        String field = mFieldEditText.getEditText().getText().toString();
        String site = mSiteEditText.getEditText().getText().toString();
        String genSetSerial = mGenSetSerialEditText.getEditText().getText().toString();
        String comments = mCommentsEditText.getEditText().getText().toString();
        String actions = mActionsEditText.getEditText().getText().toString();
        String tripReasons = mTripReasons.getEditText().getText().toString();
        Date informTime = new Date(mInformTime.getMinDate());
        Date startTime = new Date(mStartTime.getMinDate());
        return new Trip(date,
                workPermitNO,
                field,
                site,
                actions,
                genSetSerial,
                comments,
                tripReasons,
                informTime,
                startTime);
    }
}
