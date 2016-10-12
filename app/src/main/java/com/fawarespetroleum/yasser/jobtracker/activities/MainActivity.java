package com.fawarespetroleum.yasser.jobtracker.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.fragments.MainFragment;
import com.fawarespetroleum.yasser.jobtracker.fragments.OperationSelectorDialog;
import com.fawarespetroleum.yasser.jobtracker.models.Install;
import com.fawarespetroleum.yasser.jobtracker.models.Operation;
import com.fawarespetroleum.yasser.jobtracker.models.Service;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, OperationSelectorDialog.OnDialogInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Operations");
    }

    @Override
    public void onFragmentInteraction(Operation operation) {
        Log.d(TAG, "onFragmentInteraction");
        if(operation instanceof Install){
            Log.d(TAG, "onFragmentInteraction instance of install");
            Intent i = new Intent(this, InstalledActivity.class);
            i.putExtra(InstallActivity.INSTALL_TAG, (Install) operation);
            startActivity(i);
        }
        if(operation instanceof Service){
            Log.d(TAG, "onFragmentInteraction instance of service");
            Intent i = new Intent(this, ServicedActivity.class);
            i.putExtra(ServiceActivity.SERVICE_TAG, (Service) operation);
            startActivity(i);
        }
        if(operation instanceof Trip){
            Log.d(TAG, "onFragmentInteraction instance of trip");
            Intent i = new Intent(this, TripedActivity.class);
            i.putExtra(TripActivity.TRIP_TAG, (Trip) operation);
            startActivity(i);
        }
    }

    @Override
    public void onDialogInteraction(int type) {
        Intent intent;
        switch (type) {
            case Install.INSTALL_KEY:
                intent = new Intent(this, InstallActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, Install.INSTALL_KEY);
                break;
            case Service.SERVICE_KEY:
                intent = new Intent(this, ServiceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, Service.SERVICE_KEY);
                break;
            case Trip.TRIP_KEY:
                intent = new Intent(this, TripActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, Trip.TRIP_KEY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Install.INSTALL_KEY) {
                myRef.push().setValue(data.getParcelableExtra(InstallActivity.INSTALL_TAG));
            } else if(requestCode == Service.SERVICE_KEY){
                myRef.push().setValue(data.getParcelableExtra(ServiceActivity.SERVICE_TAG));
            } else if(requestCode == Trip.TRIP_KEY){
                myRef.push().setValue(data.getParcelableExtra(TripActivity.TRIP_TAG));
            }
        }
    }
}
