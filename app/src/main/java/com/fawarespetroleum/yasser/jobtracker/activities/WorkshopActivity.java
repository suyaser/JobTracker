package com.fawarespetroleum.yasser.jobtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.adapters.GeneratorListAdapter;
import com.fawarespetroleum.yasser.jobtracker.fragments.AddGeneratorDialog;
import com.fawarespetroleum.yasser.jobtracker.fragments.AddSiteDialog;
import com.fawarespetroleum.yasser.jobtracker.models.Generator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 13/11/2016.
 */
public class WorkshopActivity extends AppCompatActivity implements AddGeneratorDialog.OnAddGeneratorDialogListener, GeneratorListAdapter.OnGenClickListener{

    @BindView(R.id.MenuBar)
    Toolbar mToolBar;
    @BindView(R.id.generatorsRecyclerView)
    RecyclerView mGeneratorsRecyclerView;

    ArrayList<Generator> mGenerators;
    GeneratorListAdapter adapter;

    Unbinder unbinder;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mGenerators = new ArrayList<>();
        adapter = new GeneratorListAdapter(this, mGenerators);

        mGeneratorsRecyclerView.setAdapter(adapter);
        mGeneratorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference("generators");
        Query query = mDatabase.orderByChild("inWorkshop").equalTo(true);

        queryDB(query);
    }

    private void queryDB(Query query) {
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mGenerators.add(dataSnapshot.getValue(Generator.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
            case R.id.action_add_site:
                fm = getSupportFragmentManager();
                AddGeneratorDialog addGeneratorDialog = new AddGeneratorDialog();
                addGeneratorDialog.show(fm, "add_generator");
                return true;
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
    public void onAddGenerator(String serial, int size) {
        mDatabase.child(serial).setValue(new Generator(serial, size, true));
    }

    @Override
    public void startGenActivity(int position) {
        Intent i = new Intent(this, GeneratorActivity.class);
        i.putExtra(GeneratorActivity.GENERATOR_KEY, mGenerators.get(position));
        startActivity(i);
    }
}
