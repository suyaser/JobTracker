package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fawarespetroleum.yasser.jobtracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yasser on 09/10/2016.
 */

public class SignupActivity extends AppCompatActivity{

    private static final String TAG = SignupActivity.class.getSimpleName();
    private static final int REQUEST_SIGNUP = 0;


    @BindView(R.id.name)
    TextInputEditText mNameEditText;
    @BindView(R.id.email)
    TextInputEditText mEmailEditText;
    @BindView(R.id.password)
    TextInputEditText mPasswordEditText;
    @BindView(R.id.signup_progress)
    ProgressBar mSignUpProgressBar;
    @BindView(R.id.sign_in_text_view)
    TextView mSignInTextView;
    @BindView(R.id.email_sign_up_button)
    Button mSignUpButton;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        unbinder = ButterKnife.bind(this);
    }

    public void Signup(View view) {
        Log.d(TAG, "Signup");


        mSignUpButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        onSignupSuccess();
        progressDialog.dismiss();
    }

    public void onSignupSuccess() {
        mSignUpButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mSignUpButton.setEnabled(true);
    }

}
