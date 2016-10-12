package com.fawarespetroleum.yasser.jobtracker.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.fragments.ServicedFragment;
import com.fawarespetroleum.yasser.jobtracker.fragments.TripedFragment;
import com.fawarespetroleum.yasser.jobtracker.models.Service;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;

public class TripedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triped);

        Trip trip = (Trip) getIntent().getParcelableExtra(TripActivity.TRIP_TAG);
        if (savedInstanceState == null) {
            startServicedFragment(trip);
        }
    }

    private void startServicedFragment(Trip trip) {
        TripedFragment fragment = new TripedFragment();
        Bundle args = new Bundle();
        args.putParcelable(TripActivity.TRIP_TAG, trip);
        fragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.TripedFragment, fragment);
        ft.commit();
    }
}
