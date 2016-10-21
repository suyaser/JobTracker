package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TripActivity extends AppCompatActivity {
    private static final String TAG = ServiceActivity.class.getSimpleName();
    public static final String TRIP_TAG = "Trip key";

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
    @BindView(R.id.InformEditText)
    TextInputEditText mInformTimeEditText;
    @BindView(R.id.StartTimeEditText)
    TextInputEditText mStartTimeEditText;

    private TimePickerDialog informTimePickerDialog;
    private TimePickerDialog startTimePickerDialog;
    private SimpleDateFormat dateFormatter;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        unbinder = ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.MenuBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mInformTimeEditText.setInputType(InputType.TYPE_NULL);
        mInformTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informTimePickerDialog.show();
            }
        });
        mStartTimeEditText.setInputType(InputType.TYPE_NULL);
        mStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimePickerDialog.show();
            }
        });
        dateFormatter = new SimpleDateFormat("HH:mm");
        setDateTimeField();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sumbit:
                Intent returnIntent = new Intent();
                returnIntent.putExtra(TRIP_TAG, getTripObject());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        Date informTime = null;
        try {
            informTime = mInformTimeEditText.getText().toString().matches("") ? null :
                    dateFormatter.parse(mInformTimeEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date startTime = null;
        try {
            startTime = mStartTimeEditText.getText().toString().matches("") ? null :
                    dateFormatter.parse(mStartTimeEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        informTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR, hour);
                newDate.set(Calendar.MINUTE, minute);
                mInformTimeEditText.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);
        startTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR, hour);
                newDate.set(Calendar.MINUTE, minute);
                mStartTimeEditText.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);
    }
}
