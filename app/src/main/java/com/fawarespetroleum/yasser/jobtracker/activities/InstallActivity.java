package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.fragments.OperationSelectorDialog;
import com.fawarespetroleum.yasser.jobtracker.models.Install;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 11/10/2016.
 */

public class InstallActivity extends AppCompatActivity {

    private static final String TAG = InstallActivity.class.getSimpleName();
    public static final String INSTALL_TAG = "Insert key";

    @BindView(R.id.WorkPermitEditText)
    TextInputLayout mWorkPermitEditText;
    @BindView(R.id.FieldEditText)
    TextInputLayout mFieldEditText;
    @BindView(R.id.SiteEditText)
    TextInputLayout mSiteEditText;
    @BindView(R.id.ContractorEditText)
    TextInputLayout mContractorEditText;
    @BindView(R.id.GenSetSerialEditText)
    TextInputLayout mGenSetSerialEditText;
    @BindView(R.id.GenSetSizeEditText)
    TextInputLayout mGenSetSizeEditText;
    @BindView(R.id.TankSerialEditText)
    TextInputLayout mTankSerialEditText;
    @BindView(R.id.SyncPanelEditText)
    TextInputLayout mSyncPanelEditText;
    @BindView(R.id.FireExtinguisherEditText)
    TextInputLayout mFireExtinguisherEditText;
    @BindView(R.id.FEExpireDateEditText)
    TextInputEditText mFEExpireDateEditText;
    @BindView(R.id.CommentsEditText)
    TextInputLayout mCommentsEditText;

    private DatePickerDialog expiryDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        unbinder = ButterKnife.bind(this);

        mFEExpireDateEditText.setInputType(InputType.TYPE_NULL);
        mFEExpireDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expiryDatePickerDialog.show();
            }
        });
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        setDateTimeField();

        Toolbar toolbar = (Toolbar) findViewById(R.id.MenuBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
                returnIntent.putExtra(INSTALL_TAG, getInstallObject());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Install getInstallObject() {
        Date date = new Date();
        String workPermitNO = mWorkPermitEditText.getEditText().getText().toString();
        String field = mFieldEditText.getEditText().getText().toString();
        String site = mSiteEditText.getEditText().getText().toString();
        String contractor  = mContractorEditText.getEditText().getText().toString();
        String genSetSerial = mGenSetSerialEditText.getEditText().getText().toString();
        int genSetSize = mGenSetSizeEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mGenSetSizeEditText.getEditText().getText().toString());
        String tankSize = mTankSerialEditText.getEditText().getText().toString();
        String syncPanel = mSyncPanelEditText.getEditText().getText().toString();
        String fireExtinguisher = mFireExtinguisherEditText.getEditText().getText().toString();
        Date feExpiryDate = null;
        try {
            feExpiryDate = mFEExpireDateEditText.getText().toString().matches("") ? null :
                    dateFormatter.parse(mFEExpireDateEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String comments = mCommentsEditText.getEditText().getText().toString();
        return new Install(date,
                workPermitNO,
                field,
                site,
                contractor,
                genSetSerial,
                genSetSize,
                tankSize,
                syncPanel,
                fireExtinguisher,
                feExpiryDate,
                comments);
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        expiryDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mFEExpireDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
