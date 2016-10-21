package com.fawarespetroleum.yasser.jobtracker.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.fragments.InstalledFragment;
import com.fawarespetroleum.yasser.jobtracker.fragments.ServicedFragment;
import com.fawarespetroleum.yasser.jobtracker.models.Install;
import com.fawarespetroleum.yasser.jobtracker.models.Service;

public class ServicedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviced);

        Service service = getIntent().getParcelableExtra(ServiceActivity.SERVICE_TAG);
        if (savedInstanceState == null) {
            startServicedFragment(service);
        }
    }

    private void startServicedFragment(Service service) {
        ServicedFragment fragment = new ServicedFragment();
        Bundle args = new Bundle();
        args.putParcelable(ServiceActivity.SERVICE_TAG, service);
        fragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ServicedFragment, fragment);
        ft.commit();
    }


}
