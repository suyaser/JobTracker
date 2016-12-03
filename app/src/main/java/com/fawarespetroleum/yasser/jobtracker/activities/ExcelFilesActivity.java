package com.fawarespetroleum.yasser.jobtracker.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import jxl.write.WriteException;

/**
 * Created by yasser on 21/10/2016.
 */
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
        intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(
                fileUri,
                getContentResolver().getType(fileUri));
        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No apps found to open excel sheet", Toast.LENGTH_SHORT).show();
        }

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
        intent.setType("vnd.android.cursor.dir/email");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Excel file");
        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Send email..."));
        } else {
            Toast.makeText(this, "No apps found to share the excel", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteExcelFile(String fileName, int position) {
        File file = new File(mExcelFilesDir, fileName);
        file.delete();
        mFilesList.remove(position);
        mExcelFilesAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onDialogInteraction(String excelFileName) {
        ExcelWriter test = new ExcelWriter();
        File path = new File(this.getFilesDir(), "excel");
        path.mkdir();
        File file = new File(path, excelFileName + ".xls");
        test.setOutputFile(file);
        try {
            test.write();
            mFilesList.add(excelFileName);
            mExcelFilesAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
