package com.fawarespetroleum.yasser.jobtracker.activities;

import android.content.Intent;
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

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.adapters.GeneratorListAdapter;
import com.fawarespetroleum.yasser.jobtracker.fragments.InstallGenDialog;
import com.fawarespetroleum.yasser.jobtracker.fragments.GeneratorsListFragment;
import com.fawarespetroleum.yasser.jobtracker.models.Field;
import com.fawarespetroleum.yasser.jobtracker.models.Generator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 14/11/2016.
 */

public class FieldActivity extends AppCompatActivity implements GeneratorListAdapter.OnGenClickListener{

    final public static String FIELD_TAG = "mGenerator";
    @BindView(R.id.MenuBar)
    Toolbar mToolBar;
    @BindView(R.id.fieldTextView)
    TextView mFieldTextView;
    @BindView(R.id.contractorTextView)
    TextView mContractorTextView;

    Unbinder unbinder;

    Field field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        field = getIntent().getParcelableExtra(FIELD_TAG);
        mFieldTextView.setText(String.format(getString(R.string.site_location_text),field.toString()));
        mContractorTextView.setText(String.format(getString(R.string.contractor_text),field.getmContractor()));

        FragmentManager fm = getSupportFragmentManager();
        GeneratorsListFragment fragment = new GeneratorsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(GeneratorsListFragment.QUERY_TYPE, GeneratorsListFragment.FIELD_GENERATORS);
        bundle.putString(GeneratorsListFragment.FIELD_TAG, field.toString());
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.generatorListFragment, fragment);
        fragmentTransaction.commit();
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
            case R.id.action_search:
//                Query query = mDatabase.orderByChild("mSiteName").equalTo("RA");
//                mGenerators.clear();
//                adapter.notifyDataSetChanged();
//                queryDB(query);
                return true;
//            case R.id.action_excel_files:
//                Intent i = new Intent(this, ExcelFilesActivity.class);
//                startActivity(i);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void startGenActivity(Generator generator) {
        Intent i = new Intent(this, GeneratorActivity.class);
        i.putExtra(GeneratorActivity.GENERATOR_KEY, generator);
        startActivity(i);
    }
}
