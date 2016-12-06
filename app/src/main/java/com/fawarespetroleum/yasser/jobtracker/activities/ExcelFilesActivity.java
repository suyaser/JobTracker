package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.fawarespetroleum.yasser.jobtracker.adapters.ExcelFilesListAdapter;
import com.fawarespetroleum.yasser.jobtracker.fragments.ExcelNamerDialog;
import com.fawarespetroleum.yasser.jobtracker.utils.ExcelWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExcelFilesActivity extends AppCompatActivity implements ExcelFilesListAdapter.OnFileClickListener, ExcelNamerDialog.OnDialogInteractionListener {

    @BindView(R.id.MenuBar)
    Toolbar mToolBar;
    @BindView(R.id.listRecyclerView)
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

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_excel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_excel:
                FragmentManager fm = getSupportFragmentManager();
                ExcelNamerDialog excelNamerDialog = new ExcelNamerDialog();
                excelNamerDialog.show(fm, "excel name");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(
                fileUri,
                getContentResolver().getType(fileUri));
        startActivity(Intent.createChooser(intent, "Send email..."));

    }

    @Override
    public void shareExcelFile(String fileName) {
        File file = new File(mExcelFilesDir, fileName);
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri fileUri = FileProvider.getUriForFile(
                ExcelFilesActivity.this,
                "com.fawarespetroleum.yasser.jobtracker.fileprovider",
                file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType(getContentResolver().getType(fileUri));
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        startActivity(Intent.createChooser(intent, "Send email..."));
    }

    @Override
    public void deleteExcelFile(String fileName, int position) {
        File file = new File(mExcelFilesDir, fileName);
        if (file.exists()) {
            file.delete();
            mFilesList.remove(position);
            mExcelFilesAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void onDialogInteraction(String excelFileName) {
        ExcelWriter write = new ExcelWriter();
        File path = new File(this.getFilesDir(), "excel");
        path.mkdir();
        File file = new File(path, excelFileName + ".xls");
        try {
            write.createExcel(file);
            mFilesList.add(excelFileName + ".xls");
            mExcelFilesAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
