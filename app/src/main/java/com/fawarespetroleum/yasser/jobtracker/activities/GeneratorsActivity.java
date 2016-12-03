package com.fawarespetroleum.yasser.jobtracker.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.adapters.GeneratorListAdapter;
import com.fawarespetroleum.yasser.jobtracker.fragments.GeneratorsListFragment;
import com.fawarespetroleum.yasser.jobtracker.models.Generator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GeneratorsActivity extends AppCompatActivity implements GeneratorListAdapter.OnGenClickListener{

    @BindView(R.id.MenuBar)
    Toolbar mToolBar;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generators);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FragmentManager fm = getSupportFragmentManager();
        GeneratorsListFragment fragment = new GeneratorsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(GeneratorsListFragment.QUERY_TYPE, GeneratorsListFragment.GENERATORS_ALL);
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
            case android.R.id.home:
                onBackPressed();
                return true;
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
