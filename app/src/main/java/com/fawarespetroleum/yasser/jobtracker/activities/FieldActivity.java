package com.fawarespetroleum.yasser.jobtracker.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.adapters.FieldListAdapter;
import com.fawarespetroleum.yasser.jobtracker.adapters.GeneratorListAdapter;
import com.fawarespetroleum.yasser.jobtracker.fragments.AddGenDialog;
import com.fawarespetroleum.yasser.jobtracker.fragments.AddGeneratorDialog;
import com.fawarespetroleum.yasser.jobtracker.models.Field;
import com.fawarespetroleum.yasser.jobtracker.models.Generator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 14/11/2016.
 */

public class FieldActivity extends AppCompatActivity implements AddGenDialog.OnAddGeneratorDialogListener, GeneratorListAdapter.OnGenClickListener{

    final public static String FIELD_TAG = "mGenerator";
    @BindView(R.id.MenuBar)
    Toolbar mToolBar;
    @BindView(R.id.generatorRecyclerView)
    RecyclerView mGeneratorsRecyclerView;
    @BindView(R.id.fieldTextView)
    TextView mFieldTextView;
    @BindView(R.id.contractorTextView)
    TextView mContractorTextView;
    @BindView(R.id.addGenButton)
    Button mAddGenButton;

    ArrayList<Generator> mGenerators;
    GeneratorListAdapter adapter;

    Unbinder unbinder;

    Field field;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mGenerators = new ArrayList<>();
        adapter = new GeneratorListAdapter(this, mGenerators);

        mGeneratorsRecyclerView.setAdapter(adapter);
        mGeneratorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        field = getIntent().getParcelableExtra(FIELD_TAG);
        mFieldTextView.setText(field.toString());
        mContractorTextView.setText(field.getmContractor());
        mAddGenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                AddGenDialog addGeneratorDialog = new AddGenDialog();
                addGeneratorDialog.show(fm, "add_generator");
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("generators");
        Query query = mDatabase.orderByChild("site").equalTo(field.toString());

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
                Query query = mDatabase.orderByChild("mSiteName").equalTo("RA");
                mGenerators.clear();
                adapter.notifyDataSetChanged();
                queryDB(query);
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
    public void onAddGenerator(final String serial) {
        mDatabase.orderByKey().equalTo(serial).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    mDatabase.child(serial).child("site").setValue(field.toString());
                    mDatabase.child(serial).child("inWorkshop").setValue(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void startGenActivity(int position) {

    }
}
