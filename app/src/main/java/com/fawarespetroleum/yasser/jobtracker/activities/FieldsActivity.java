package com.fawarespetroleum.yasser.jobtracker.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.adapters.FieldListAdapter;
import com.fawarespetroleum.yasser.jobtracker.fragments.AddSiteDialog;
import com.fawarespetroleum.yasser.jobtracker.models.Field;
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

public class FieldsActivity extends AppCompatActivity implements AddSiteDialog.OnAddSiteDialogListener, FieldListAdapter.OnClickListener{

    @BindView(R.id.MenuBar)
    Toolbar mToolBar;
    @BindView(R.id.fieldsRecyclerView)
    RecyclerView mFieldsRecyclerView;

    ArrayList<Field> mFields;
    FieldListAdapter adapter;

    Unbinder unbinder;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mFields = new ArrayList<>();
        adapter = new FieldListAdapter(this, mFields);

        mFieldsRecyclerView.setAdapter(adapter);
        mFieldsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference("fields");
        Query query = mDatabase;

        queryDB(query);

    }

    private void queryDB(Query query) {
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mFields.add(dataSnapshot.getValue(Field.class));
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
        inflater.inflate(R.menu.menu_search_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm;
        switch (item.getItemId()) {
            case R.id.action_add:
                fm = getSupportFragmentManager();
                AddSiteDialog addSiteDialog = new AddSiteDialog();
                addSiteDialog.show(fm, "add_site");
                return true;
            case R.id.action_search:
//                Query query = mDatabase.orderByChild("mSiteName").equalTo("RA");
//                mFields.clear();
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
    public void onAddSite(String site, String contractor) {
        String[] sit = site.split("_");
        Field field = new Field(sit[1], sit[0], contractor);
        mDatabase.child(field.toString()).setValue(field);
    }

    @Override
    public void startFieldActivity(int position) {
        Intent i = new Intent(this, FieldActivity.class);
        i.putExtra(FieldActivity.FIELD_TAG, mFields.get(position));
        startActivity(i);
    }
}
