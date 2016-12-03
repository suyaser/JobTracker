package com.fawarespetroleum.yasser.jobtracker.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.fragments.InstallGenDialog;
import com.fawarespetroleum.yasser.jobtracker.fragments.InstalledGenDialog;
import com.fawarespetroleum.yasser.jobtracker.fragments.OperationListFragment;
import com.fawarespetroleum.yasser.jobtracker.fragments.ServiceGenDialog;
import com.fawarespetroleum.yasser.jobtracker.fragments.ServicedGenDialog;
import com.fawarespetroleum.yasser.jobtracker.fragments.TripGenDialog;
import com.fawarespetroleum.yasser.jobtracker.fragments.TripedGenDialog;
import com.fawarespetroleum.yasser.jobtracker.models.Generator;
import com.fawarespetroleum.yasser.jobtracker.models.Install;
import com.fawarespetroleum.yasser.jobtracker.models.Operation;
import com.fawarespetroleum.yasser.jobtracker.models.Service;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GeneratorActivity extends AppCompatActivity implements InstallGenDialog.OnAddGeneratorDialogListener, TripGenDialog.OnTripGeneratorDialogListener, ServiceGenDialog.OnServiceGeneratorDialogListener, OperationListFragment.OnFragmentInteractionListener {
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
    TextView mSyncPaneTextView;
    @BindView(R.id.FireExtTextView)
    TextView mFireExtTextView;
    @BindView(R.id.FETextView)
    TextView mFETextView;
    @BindView(R.id.siteTextView)
    TextView mSiteTextView;
    @BindView(R.id.installButton)
    Button mInstallButton;
    @BindView(R.id.tripButton)
    Button mTripButton;
    @BindView(R.id.serviceButton)
    Button mServiceButton;

    Unbinder unbinder;

    Generator mGenerator;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mGenerator = getIntent().getParcelableExtra(GENERATOR_KEY);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        OperationListFragment fragment = new OperationListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(OperationListFragment.GENERATOR_TAG, mGenerator.getmSerial());
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.operationListFragment, fragment);
        fragmentTransaction.commit();

        populate();
    }

    private void populate() {
        mGenSerialTextView.setText(String.format(getString(R.string.gen_serial_text),
                mGenerator.getmSerial()));
        mGenSizeTextView.setText(String.format(getString(R.string.gen_size_text),
                String.valueOf(mGenerator.getmSize())));
        mTankSerialTextView.setText(String.format(getString(R.string.tank_serial_text),
                mGenerator.getTankSerial() == null || mGenerator.getTankSerial().isEmpty()
                        ? getString(R.string.n_a) : mGenerator.getTankSerial()));
        mFETextView.setText(String.format(getString(R.string.fe_expiry_text),
                mGenerator.getFEExpiryDate() == null ? getString(R.string.n_a) : mGenerator.getFEExpiryDate().toString()));
        mFireExtTextView.setText(String.format(getString(R.string.fire_extinguisher_text),
                mGenerator.getFireExtinguisher() == null || mGenerator.getFireExtinguisher().isEmpty()
                        ? getString(R.string.n_a) : mGenerator.getFireExtinguisher()));
        mSyncPaneTextView.setText(String.format(getString(R.string.sync_panel_text),
                mGenerator.getSyncPanel() == null || mGenerator.getSyncPanel().isEmpty()
                        ? getString(R.string.n_a) : mGenerator.getFireExtinguisher()));
        mSiteTextView.setText(String.format(getString(R.string.site_location_text),
                mGenerator.isInWorkshop() ? "Workshop" : mGenerator.getSite()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void installGenerator(View view) {
        FragmentManager fm = getSupportFragmentManager();
        InstallGenDialog installGenDialog = new InstallGenDialog();
        installGenDialog.show(fm, "install_generator");
    }

    public void serviceGenerator(View view) {
        FragmentManager fm = getSupportFragmentManager();
        ServiceGenDialog serviceGenDialog = new ServiceGenDialog();
        serviceGenDialog.show(fm, "service_generator");
    }

    public void tripGenerator(View view) {
        FragmentManager fm = getSupportFragmentManager();
        TripGenDialog tripGenDialog = new TripGenDialog();
        tripGenDialog.show(fm, "trip_generator");
    }

    @Override
    public void onAddGenerator(final Install install) {
        mDatabase.child("fields").orderByKey().equalTo(install.getField()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    install.setGenerator(mGenerator.getmSerial());
                    mGenerator.install(install);
                    mDatabase.child("generators").child(mGenerator.getmSerial()).setValue(mGenerator);
                    mDatabase.child("operations").child(install.getWorkPermitNumber()).setValue(install);
                    populate();
                } else {
                    Toast.makeText(GeneratorActivity.this, "Install failed! make you sure to enter an existing field", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onServiceGenerator(Service service) {
        service.setGenerator(mGenerator.getmSerial());
        mDatabase.child("operations").child(service.getWorkPermitNumber()).setValue(service);
    }

    @Override
    public void onTripGenerator(Trip trip) {
        trip.setGenerator(mGenerator.getmSerial());
        mDatabase.child("operations").child(trip.getWorkPermitNumber()).setValue(trip);
    }

    @Override
    public void onFragmentInteraction(Operation operation) {
        FragmentManager fm;
        Bundle bundle;
        switch (operation.getType()) {
            case Install.INSTALL_KEY:
                fm = getSupportFragmentManager();
                InstalledGenDialog installedGenDialog = new InstalledGenDialog();
                bundle = new Bundle();
                bundle.putParcelable(InstalledGenDialog.INSTALL_TAG, (Install) operation);
                installedGenDialog.setArguments(bundle);
                installedGenDialog.show(fm, "installed");
                break;
            case Service.SERVICE_KEY:
                fm = getSupportFragmentManager();
                ServicedGenDialog servicedGenDialog = new ServicedGenDialog();
                bundle = new Bundle();
                bundle.putParcelable(ServicedGenDialog.SERVICE_TAG, (Service) operation);
                servicedGenDialog.setArguments(bundle);
                servicedGenDialog.show(fm, "serviced");
                break;
            case Trip.TRIP_KEY:
                fm = getSupportFragmentManager();
                TripedGenDialog tripedGenDialog = new TripedGenDialog();
                bundle = new Bundle();
                bundle.putParcelable(TripedGenDialog.TRIP_TAG, (Trip) operation);
                tripedGenDialog.setArguments(bundle);
                tripedGenDialog.show(fm, "trip");
                break;
        }
    }

}
