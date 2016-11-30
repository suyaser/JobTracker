package com.fawarespetroleum.yasser.jobtracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Generator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 14/11/2016.
 */
public class AddGenDialog extends DialogFragment {

    @BindView(R.id.generatorSerialEditText)
    AutoCompleteTextView mGeneratorSerialEditText;

    Unbinder unbinder;
    private OnAddGeneratorDialogListener mListener;
    private DatabaseReference mDatabase;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_gen, null);

        unbinder = ButterKnife.bind(this, view);

        return builder.setView(view)
                .setTitle("new Generator")
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mListener.onAddGenerator(mGeneratorSerialEditText.getText().toString());
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                )
                .create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final ArrayList<String> generators = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("generators");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    generators.add(snapshot.getValue(Generator.class).getmSerial());
                }
                Log.d("ddddd", String.valueOf(generators.get(0)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, generators);
        mGeneratorSerialEditText.setAdapter(adapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddGeneratorDialogListener) {
            mListener = (OnAddGeneratorDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddGeneratorDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        unbinder.unbind();

    }

    public interface OnAddGeneratorDialogListener {
        void onAddGenerator(String serial);
    }
}
