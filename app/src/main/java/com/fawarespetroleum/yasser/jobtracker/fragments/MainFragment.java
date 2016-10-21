package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment implements OperationListAdapter.OnOperationClickListener {

    private static final String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.OperationsList)
    RecyclerView mOperationRecyclerView;

    Unbinder unbinder;
    private OperationListAdapter mOperationListAdapter;
    private ArrayList<Operation> mOperationList;
    private OnFragmentInteractionListener mListener;
    DatabaseReference myRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Operations");

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
