package com.fawarespetroleum.yasser.jobtracker.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Field;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 13/11/2016.
 */

public class FieldListAdapter extends RecyclerView.Adapter<FieldListAdapter.ViewHolder>{

    private ArrayList<Field> mFields;
    private LayoutInflater inflater;
    private OnClickListener mListener;
    private Activity activity;

    public FieldListAdapter(Activity activity, ArrayList<Field> fields) {
        mFields = fields;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        mListener = (OnClickListener)activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.field_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Field field = mFields.get(position);
        holder.mFieldTextView.setText(String.format(activity.getString(R.string.site_location_text),field.getmSiteName()+"_"+field.getmFieldName()));
        holder.mContractorTextView.setText(String.format(activity.getString(R.string.contractor_text),field.getmContractor()));
    }

    @Override
    public int getItemCount() {
        return mFields.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.FieldListCardView)
        CardView mCardView;

        @BindView(R.id.fieldTextView)
        TextView mFieldTextView;

        @BindView(R.id.ContractorTextView)
        TextView mContractorTextView;

        Unbinder unbinder;

    public ViewHolder(View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);

        mCardView.setOnClickListener(this);
    }

        @Override
        public void onClick(View view) {
            mListener.startFieldActivity(getAdapterPosition());
        }
    }

    public interface OnClickListener {
        void startFieldActivity(int position);
    }
}