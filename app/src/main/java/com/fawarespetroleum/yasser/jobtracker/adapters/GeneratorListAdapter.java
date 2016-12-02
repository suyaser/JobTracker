package com.fawarespetroleum.yasser.jobtracker.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.models.Generator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 13/11/2016.
 */
public class GeneratorListAdapter extends RecyclerView.Adapter<GeneratorListAdapter.ViewHolder> {

    private ArrayList<Generator> mGenerator;
    private LayoutInflater inflater;
    private OnGenClickListener mListener;
    private Activity activity;

    public GeneratorListAdapter(Activity activity, ArrayList<Generator> generators) {
        mGenerator = generators;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        mListener = (OnGenClickListener) activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.generator_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Generator generator = mGenerator.get(position);
        holder.mGeneratorsSerialTextView.setText(String.format(activity.getString(R.string.gen_serial_text,generator.getmSerial())));
        holder.mGeneratorsSizeTextView.setText(String.format(activity.getString(R.string.gen_size_text,String.valueOf(generator.getmSize()))));
        holder.mGeneratorsLocationTextView.setText(String.format(activity.getString(R.string.site_location_text,
                generator.isInWorkshop() ? "Workshop" : generator.getSite().toString())));
    }

    public void unbind(){
        inflater = null;
        mListener = null;
        activity = null;
    }
    @Override
    public int getItemCount() {
        return mGenerator.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.GeneratorListCardView)
        CardView mGeneratorListCardView;

        @BindView(R.id.generatorSerialTextView)
        TextView mGeneratorsSerialTextView;

        @BindView(R.id.generatorSizeTextView)
        TextView mGeneratorsSizeTextView;

        @BindView(R.id.generatorLocationTextView)
        TextView mGeneratorsLocationTextView;

        Unbinder unbinder;

        public ViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

            mGeneratorListCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.startGenActivity(mGenerator.get(getAdapterPosition()));
        }
    }

    public interface OnGenClickListener {
        void startGenActivity(Generator generator);
    }
}