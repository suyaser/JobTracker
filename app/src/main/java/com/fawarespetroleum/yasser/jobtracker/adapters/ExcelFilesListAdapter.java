package com.fawarespetroleum.yasser.jobtracker.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.activities.ExcelFilesActivity;
import com.fawarespetroleum.yasser.jobtracker.models.Operation;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yasser on 21/10/2016.
 */
public class ExcelFilesListAdapter extends RecyclerView.Adapter<ExcelFilesListAdapter.FileViewHolder>{

    private final LayoutInflater mInflater;
    private ArrayList<String> files;
    private ExcelFilesListAdapter.OnFileClickListener onFileClickListener;

    public ExcelFilesListAdapter(ExcelFilesActivity activity, ArrayList<String> files) {
        mInflater = LayoutInflater.from(activity);
        this.files = files;
        onFileClickListener = activity;
    }
    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.excel_list_item, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        String name = files.get(position);
        holder.mExcelFileName.setText(name);
    }


    @Override
    public int getItemCount() {
        return files.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ExcelFileName)
        TextView mExcelFileName;
        @BindView(R.id.ExcelFileShare)
        ImageView mExcelFileShare;
        @BindView(R.id.ExcelFileDelete)
        ImageView mExcelFileDelete;
        public FileViewHolder(View OperationView)  {
            super(OperationView);
            ButterKnife.bind(this, OperationView);
            mExcelFileName.setOnClickListener(this);
            mExcelFileShare.setOnClickListener(this);
            mExcelFileDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view == mExcelFileName){
                onFileClickListener.openExcelFile(mExcelFileName.getText().toString());
            }else if(view == mExcelFileShare){
                onFileClickListener.shareExcelFile(mExcelFileName.getText().toString());
            }else if(view == mExcelFileDelete){
                onFileClickListener.deleteExcelFile(mExcelFileName.getText().toString(), getAdapterPosition());
            }
        }
    }

    public interface OnFileClickListener {
        void openExcelFile(String fileName);
        void shareExcelFile(String fileName);
        void deleteExcelFile(String fileName, int postion);
    }

}
