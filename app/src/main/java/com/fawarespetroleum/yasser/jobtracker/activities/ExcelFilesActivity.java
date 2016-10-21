package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.adapters.ExcelFilesListAdapter;
import com.fawarespetroleum.yasser.jobtracker.adapters.OperationListAdapter;
import com.fawarespetroleum.yasser.jobtracker.models.Operation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 21/10/2016.
 */
public class ExcelFilesActivity extends AppCompatActivity implements ExcelFilesListAdapter.OnFileClickListener {

    @BindView(R.id.ExcelFilesRecyclerView)
    RecyclerView mExcelFilesRecyclerView;

    private ExcelFilesListAdapter mExcelFilesAdapter;
    private List<String> mFilesList;
    private File mExcelFilesDir;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_excel_main);

        unbinder = ButterKnife.bind(this);

        mExcelFilesDir = new File(this.getFilesDir(), "excel");

        if (mExcelFilesDir.list() != null) {
            mFilesList = new ArrayList<String>(Arrays.asList(mExcelFilesDir.list()));
        } else {
            mFilesList = new ArrayList<>();
        }

        mExcelFilesAdapter = new ExcelFilesListAdapter(this, (ArrayList<String>) mFilesList);
        mExcelFilesRecyclerView.setAdapter(mExcelFilesAdapter);
        mExcelFilesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void openExcelFile(String fileName) {
        File file = new File(mExcelFilesDir, fileName);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri fileUri = FileProvider.getUriForFile(
                ExcelFilesActivity.this,
                "com.fawarespetroleum.yasser.jobtracker.fileprovider",
                file);
        intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(
                fileUri,
                getContentResolver().getType(fileUri));
        startActivity(intent);

    }

    @Override
    public void shareExcelFile(String fileName) {
        File file = new File(mExcelFilesDir, fileName);
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri fileUri = FileProvider.getUriForFile(
                ExcelFilesActivity.this,
                "com.fawarespetroleum.yasser.jobtracker.fileprovider",
                file);
        intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(
                fileUri,
                getContentResolver().getType(fileUri));
        startActivity(intent);
        intent.setType("vnd.android.cursor.dir/email");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Excel file");
        startActivity(Intent.createChooser(intent, "Send email..."));
    }

    @Override
    public void deleteExcelFile(String fileName, int position) {
        File file = new File(mExcelFilesDir, fileName);
        file.delete();
        mFilesList.remove(position);
        mExcelFilesAdapter.notifyItemRemoved(position);
    }
}
