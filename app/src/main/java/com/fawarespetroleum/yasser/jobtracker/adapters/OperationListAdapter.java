package com.fawarespetroleum.yasser.jobtracker.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Operation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yasser on 07/10/2016.
 */
public class OperationListAdapter extends RecyclerView.Adapter<OperationListAdapter.OperationViewHolder> {
    private final LayoutInflater mInflater;
    private ArrayList<Operation> Operations;
    private android.text.format.DateFormat mDataFormat;
    private OnOperationClickListener onOperationClickListener;
    private Fragment fragment;

    public OperationListAdapter(Fragment fragment, ArrayList<Operation> operations) {
        mInflater = LayoutInflater.from(fragment.getContext());
        this.Operations = operations;
        this.fragment = fragment;
        onOperationClickListener = (OnOperationClickListener) fragment;
        mDataFormat = new DateFormat();
    }

    @Override
    public OperationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.operation_list_item, parent, false);
        return new OperationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OperationViewHolder holder, int position) {
        Operation operation = Operations.get(position);

        holder.mPublishDateTextView.setText(
                String.format(fragment.getResources().getString(R.string.publish_date_text)
        , DateFormat.format("dd-MM-yyyy", operation.getDate())));

        holder.mWorkPermitTextView.setText(
                String.format(fragment.getResources().getString(R.string.work_permit_text)
                ,operation.getWorkPermitNumber()));
        holder.mOperationTypeTextView.setText(
                String.format(fragment.getResources().getString(R.string.operation_type_text)
                ,operation.getType() == 1 ? "Install": operation.getType() == 2 ? "service": "trip"));

    }

    @Override
    public int getItemCount() {
        return Operations.size();
    }

    class OperationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.OperationListCardView)
        CardView mOperationListCardView;

        @BindView(R.id.OperationTypeTextView)
        TextView mOperationTypeTextView;

        @BindView(R.id.WorkPermitTextView)
        TextView mWorkPermitTextView;

        @BindView(R.id.PublishDateTextView)
        TextView mPublishDateTextView;

        public OperationViewHolder(View OperationView)  {
            super(OperationView);
            ButterKnife.bind(this, OperationView);
            mOperationListCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onOperationClickListener.onOperationClick(view, getAdapterPosition());
        }
    }

    public interface OnOperationClickListener {
        void onOperationClick(View view, int position);
    }
}