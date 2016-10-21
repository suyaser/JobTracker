package com.fawarespetroleum.yasser.jobtracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.fragments.ExcelNamerDialog;
import com.fawarespetroleum.yasser.jobtracker.fragments.MainFragment;
import com.fawarespetroleum.yasser.jobtracker.fragments.OperationSelectorDialog;
import com.fawarespetroleum.yasser.jobtracker.models.Install;
import com.fawarespetroleum.yasser.jobtracker.models.Operation;
import com.fawarespetroleum.yasser.jobtracker.models.Service;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;
import com.fawarespetroleum.yasser.jobtracker.utils.ExcelWriter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;

import jxl.write.WriteException;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, ExcelNamerDialog.OnDialogInteractionListener, OperationSelectorDialog.OnDialogInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.MenuBar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Operations");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm;
        switch (item.getItemId()) {
            case R.id.action_add:
                fm = getSupportFragmentManager();
                OperationSelectorDialog operationSelectorDialog = new OperationSelectorDialog();
                operationSelectorDialog.show(fm, "selector Dialog");
                return true;
            case R.id.action_sign_out:
                auth.signOut();
                return true;
            case R.id.action_create_excel:
                fm = getSupportFragmentManager();
                ExcelNamerDialog excelNamerDialog = new ExcelNamerDialog();
                excelNamerDialog.show(fm, "excel namer");
                return true;
            case R.id.action_excel_files:
                Intent i = new Intent(this, ExcelFilesActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Operation operation) {
        Log.d(TAG, "onFragmentInteraction");
        if (operation instanceof Install) {
            Log.d(TAG, "onFragmentInteraction instance of install");
            Intent i = new Intent(this, InstalledActivity.class);
            i.putExtra(InstallActivity.INSTALL_TAG, (Install) operation);
            startActivity(i);
        }
        if (operation instanceof Service) {
            Log.d(TAG, "onFragmentInteraction instance of service");
            Intent i = new Intent(this, ServicedActivity.class);
            i.putExtra(ServiceActivity.SERVICE_TAG, (Service) operation);
            startActivity(i);
        }
        if (operation instanceof Trip) {
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
            } else if (requestCode == Service.SERVICE_KEY) {
                myRef.push().setValue(data.getParcelableExtra(ServiceActivity.SERVICE_TAG));
            } else if (requestCode == Trip.TRIP_KEY) {
                myRef.push().setValue(data.getParcelableExtra(TripActivity.TRIP_TAG));
            }
        }
    }

    @Override
    public void onDialogInteraction(String excelFileName) {
        ExcelWriter test = new ExcelWriter();
        File path = new File(this.getFilesDir(), "excel");
        path.mkdir();
        File file = new File(path, excelFileName+".xls");
        test.setOutputFile(file);
        try {
            test.write();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
