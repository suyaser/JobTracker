package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.adapters.OperationListAdapter;
import com.fawarespetroleum.yasser.jobtracker.models.Install;
import com.fawarespetroleum.yasser.jobtracker.models.Operation;
import com.fawarespetroleum.yasser.jobtracker.models.Service;
import com.fawarespetroleum.yasser.jobtracker.models.Trip;
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
 * Created by yasser on 02/12/2016.
 */

public class OperationListFragment extends Fragment implements OperationListAdapter.OnOperationClickListener {

    private static final String TAG = OperationListFragment.class.getSimpleName();
    public static final String GENERATOR_TAG = "generator serial";

    @BindView(R.id.listRecyclerView)
    RecyclerView mOperationRecyclerView;

    private OperationListAdapter mOperationListAdapter;
    private ArrayList<Operation> mOperationList;

    private Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private Query myRef;

    public OperationListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("operations").orderByChild("generator").equalTo(getArguments().getString(GENERATOR_TAG));

        mOperationList = new ArrayList<>();

        mOperationListAdapter = new OperationListAdapter(this, mOperationList);
        mOperationRecyclerView.setAdapter(mOperationListAdapter);
        mOperationRecyclerView.setHasFixedSize(true);
        mOperationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int type = dataSnapshot.child("type").getValue(Integer.class);
                Log.d(TAG, "operation type : " + type);
                switch (type){
                    case Install.INSTALL_KEY:
                        mOperationList.add(dataSnapshot.getValue(Install.class));
                        mOperationListAdapter.notifyItemInserted(mOperationList.size()-1);
                        break;
                    case Service.SERVICE_KEY:
                        mOperationList.add(dataSnapshot.getValue(Service.class));
                        mOperationListAdapter.notifyItemInserted(mOperationList.size()-1);
                        break;
                    case Trip.TRIP_KEY:
                        mOperationList.add(dataSnapshot.getValue(Trip.class));
                        mOperationListAdapter.notifyItemInserted(mOperationList.size()-1);
                        break;
                }
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onOperationClick(View view, int position) {
        if (mListener != null) {
            Log.d(TAG, "onOperationClick Listener position : " + position);
            mListener.onFragmentInteraction(mOperationList.get(position));
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Operation operation);
    }
}
