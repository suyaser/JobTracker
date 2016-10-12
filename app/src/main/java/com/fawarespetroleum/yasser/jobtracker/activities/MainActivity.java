package com.fawarespetroleum.yasser.jobtracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.fragments.MainFragment;
import com.fawarespetroleum.yasser.jobtracker.fragments.OperationSelectorDialog;
import com.fawarespetroleum.yasser.jobtracker.models.Operation;
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

    }

    @Override
    public void onDialogInteraction(int type) {
        Intent intent;
        switch (type) {
            case OperationSelectorDialog.INSTALL_KEY:
                intent = new Intent(this, InstallActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, OperationSelectorDialog.INSTALL_KEY);
                break;
            case OperationSelectorDialog.SERVICE_KEY:
                intent = new Intent(this, ServiceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, OperationSelectorDialog.SERVICE_KEY);
                break;
            case OperationSelectorDialog.TRIP_KEY:
                intent = new Intent(this, TripActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, OperationSelectorDialog.TRIP_KEY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == OperationSelectorDialog.INSTALL_KEY) {
                myRef.push().setValue(data.getParcelableExtra(InstallActivity.INSTALL_TAG));
            } else if(requestCode == OperationSelectorDialog.SERVICE_KEY){
                myRef.push().setValue(data.getParcelableExtra(ServiceActivity.SERVICE_TAG));
            } else if(requestCode == OperationSelectorDialog.TRIP_KEY){
                myRef.push().setValue(data.getParcelableExtra(TripActivity.TRIP_TAG));
            }
        }
    }
}
