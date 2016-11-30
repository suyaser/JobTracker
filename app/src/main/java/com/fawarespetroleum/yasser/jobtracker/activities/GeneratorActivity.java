package com.fawarespetroleum.yasser.jobtracker.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Field;
import com.fawarespetroleum.yasser.jobtracker.models.Generator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GeneratorActivity extends AppCompatActivity {
    public static final String GENERATOR_KEY = "generator";

    @BindView(R.id.MenuBar)
    Toolbar mToolBar;
    @BindView(R.id.genSerialTextView)
    TextView mGenSerialTextView;
    @BindView(R.id.genSizeTextView)
    TextView mGenSizeTextView;
    @BindView(R.id.tankSerialTextView)
    TextView mTankSerialTextView;
    @BindView(R.id.syncPanelTextView)
    TextView mSyncPaneTextViewl;
    @BindView(R.id.FireExtTextView)
    TextView mFireExtTextView;
    @BindView(R.id.FETextView)
    TextView mFETextView;
    @BindView(R.id.siteTextView)
    TextView mSiteTextView;
    @BindView(R.id.tripButton)
    Button mTripButton;
    @BindView(R.id.serviceButton)
    Button mServiceButton;

    Unbinder unbinder;

    Generator mGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mGenerator = getIntent().getParcelableExtra(GENERATOR_KEY);

        populate();
    }

    private void populate() {
        mGenSerialTextView.setText(mGenerator.getmSerial());
        mGenSizeTextView.setText(String.valueOf(mGenerator.getmSize()));
        mTankSerialTextView.setText(mGenerator.getTankSerial() == null ? getString(R.string.n_a) : mGenerator.getTankSerial());
        mFETextView.setText(mGenerator.getFEExpiryDate() == null ? getString(R.string.n_a) : mGenerator.getFEExpiryDate().toString());
        mFireExtTextView.setText(mGenerator.getFireExtinguisher() == null ? getString(R.string.n_a): mGenerator.getFireExtinguisher());
        mSyncPaneTextViewl.setText(mGenerator.getSyncPanel() == null ? getString(R.string.n_a) : mGenerator.getFireExtinguisher());
        mSiteTextView.setText(mGenerator.isInWorkshop() ? "Workshop" : mGenerator.getSite().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm;
        switch (item.getItemId()) {
//            case R.id.action_excel_files:
//                Intent i = new Intent(this, ExcelFilesActivity.class);
//                startActivity(i);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
