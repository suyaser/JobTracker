package com.fawarespetroleum.yasser.jobtracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.fragments.MainFragment;
import com.fawarespetroleum.yasser.jobtracker.fragments.OperationSelectorDialog;
import com.fawarespetroleum.yasser.jobtracker.models.Install;
import com.fawarespetroleum.yasser.jobtracker.models.Operation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, OperationSelectorDialog.OnDialogInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_Install = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(Operation operation) {

    }

    @Override
    public void onDialogInteraction(int type) {
        Intent intent;
        switch (type){
            case OperationSelectorDialog.INSTALL_KEY:
                intent = new Intent(this, InstallActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, REQUEST_Install);
                break;
            case OperationSelectorDialog.SERVICE_KEY:
                intent = new Intent(this, ServiceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, REQUEST_Install);
                break;
            case OperationSelectorDialog.TRIP_KEY:
                intent = new Intent(this, TripActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, REQUEST_Install);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_Install) {
            if (resultCode == RESULT_OK) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Operations");
                myRef.push().setValue(data.getParcelableExtra(InstallActivity.Install_key));
            }
        }
    }
}
