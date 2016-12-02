package com.fawarespetroleum.yasser.jobtracker.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.adapters.GeneratorListAdapter;
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
 * A simple {@link Fragment} subclass.
 */
public class GeneratorsListFragment extends Fragment{

    public static final int WORKSHOP_GENERATORS = 0;
    public static final int GENERATORS_ALL = 1;
    public static final int FIELD_GENERATORS = 2;
    public static final String QUERY_TYPE = "query type";
    public static final java.lang.String FIELD_TAG = "field";


    @BindView(R.id.listRecyclerView)
    RecyclerView mGeneratorsRecyclerView;

    ArrayList<Generator> mGenerators;
    GeneratorListAdapter adapter;

    Unbinder unbinder;
    DatabaseReference mDatabase;

    public GeneratorsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mGenerators = new ArrayList<>();
        adapter = new GeneratorListAdapter(getActivity(), mGenerators);

        mGeneratorsRecyclerView.setAdapter(adapter);
        mGeneratorsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDatabase = FirebaseDatabase.getInstance().getReference("generators");
        Query query;

        switch (getArguments().getInt(QUERY_TYPE)){
            case GENERATORS_ALL:
                query = mDatabase;
                break;
            case WORKSHOP_GENERATORS:
                query = mDatabase.orderByChild("inWorkshop").equalTo(true);
                break;
            case FIELD_GENERATORS:
                query = mDatabase.orderByChild("site").equalTo(getArguments().getString(FIELD_TAG));
                break;
            default:
                query = mDatabase;
        }

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

}
