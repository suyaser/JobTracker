package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Install;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.fawarespetroleum.yasser.jobtracker.R.string.install;

/**
 * Created by yasser on 11/10/2016.
 */

public class InstallActivity extends AppCompatActivity {

    private static final String TAG = InstallActivity.class.getSimpleName();
    public static final String Install_key = "Insert key";

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
    @BindView(R.id.FEExpireDate)
    DatePicker mFEExpireDate;
    @BindView(R.id.CommentsEditText)
    TextInputLayout mCommentsEditText;

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        unbinder = ButterKnife.bind(this);
    }

    public void SumbitData(View view) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra(Install_key, getInstallObject());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
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
        Date feExpiryDate = new Date(mFEExpireDate.getMinDate());
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
}
