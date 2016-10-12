package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Service;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = ServiceActivity.class.getSimpleName();
    public static final String SERVICE_TAG = "Service key";

    @BindView(R.id.WorkPermitEditText)
    TextInputLayout mWorkPermitEditText;
    @BindView(R.id.FieldEditText)
    TextInputLayout mFieldEditText;
    @BindView(R.id.SiteEditText)
    TextInputLayout mSiteEditText;
    @BindView(R.id.GenSetSerialEditText)
    TextInputLayout mGenSetSerialEditText;
    @BindView(R.id.RHSEditText)
    TextInputLayout mRHSEditText;
    @BindView(R.id.OilPressureEditText)
    TextInputLayout mOilPressureEditText;
    @BindView(R.id.TempEditText)
    TextInputLayout mTempEditText;
    @BindView(R.id.L2LVoltageEditText)
    TextInputLayout mL2LVoltageEditText;
    @BindView(R.id.HZEditText)
    TextInputLayout mHZEditText;
    @BindView(R.id.BatteryChargeEditText)
    TextInputLayout mBatteryChargeEditText;
    @BindView(R.id.ActionsEditText)
    TextInputLayout mActionsEditText;
    @BindView(R.id.CommentsEditText)
    TextInputLayout mCommentsEditText;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        unbinder = ButterKnife.bind(this);
    }

    public void SumbitData(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(SERVICE_TAG, getServiceObject());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private Service getServiceObject() {
        Date date = new Date();
        String workPermitNO = mWorkPermitEditText.getEditText().getText().toString();
        String field = mFieldEditText.getEditText().getText().toString();
        String site = mSiteEditText.getEditText().getText().toString();
        String genSetSerial = mGenSetSerialEditText.getEditText().getText().toString();
        String comments = mCommentsEditText.getEditText().getText().toString();
        String actions = mActionsEditText.getEditText().getText().toString();
        int RHS = mRHSEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mRHSEditText.getEditText().getText().toString());
        int oilPressure = mOilPressureEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mOilPressureEditText.getEditText().getText().toString());
        int temp = mTempEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mTempEditText.getEditText().getText().toString());
        int L2LVoltage = mL2LVoltageEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mL2LVoltageEditText.getEditText().getText().toString());
        int HZ = mHZEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mHZEditText.getEditText().getText().toString());
        int batteryCharge =  mBatteryChargeEditText.getEditText().getText().toString().matches("") ? -1 :
                Integer.parseInt(mBatteryChargeEditText.getEditText().getText().toString());
        return new Service(date,
                workPermitNO,
                field,
                site,
                actions,
                genSetSerial,
                comments,
                RHS,
                oilPressure,
                temp,
                L2LVoltage,
                HZ,
                batteryCharge);
    }
}
