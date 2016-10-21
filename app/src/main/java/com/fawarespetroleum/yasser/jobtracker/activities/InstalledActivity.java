
package com.fawarespetroleum.yasser.jobtracker.activities;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.fragments.InstalledFragment;
import com.fawarespetroleum.yasser.jobtracker.models.Install;

public class InstalledActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed);

        Install install = getIntent().getParcelableExtra(InstallActivity.INSTALL_TAG);
        if (savedInstanceState == null) {
            startInstalledFragment(install);
        }
    }

    private void startInstalledFragment(Install install) {
        InstalledFragment fragment = new InstalledFragment();
        Bundle args = new Bundle();
        args.putParcelable(InstallActivity.INSTALL_TAG, install);
        fragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.InstalledFragment, fragment);
        ft.commit();
    }

}
