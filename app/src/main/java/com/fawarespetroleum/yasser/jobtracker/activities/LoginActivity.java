package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fawarespetroleum.yasser.jobtracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.email)
    TextInputEditText mEmailEditText;
    @BindView(R.id.password)
    TextInputEditText mPasswordEditText;
    @BindView(R.id.login_progress)
    ProgressBar mSignInProgressBar;
    @BindView(R.id.sign_Up_text_view)
    TextView mSignUpTextView;
    @BindView(R.id.email_sign_In_button)
    Button mSignInButton;

    Unbinder unbinder;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        unbinder = ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(mAuthListener);
    }

    public void SignIn(View view) {
        String email = mEmailEditText.getEditableText().toString();
        String password = mPasswordEditText.getEditableText().toString();

        if(!checkEmail(email)) {
            return;
        }
        if(!checkPassword(password)) {
            return;
        }

        mSignInProgressBar.setVisibility(View.VISIBLE);
        mSignInButton.setEnabled(false);
        mEmailEditText.setEnabled(false);
        mPasswordEditText.setEnabled(false);
        mSignUpTextView.setEnabled(false);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mSignInProgressBar.setVisibility(View.GONE);
                        mSignInButton.setEnabled(true);
                        mEmailEditText.setEnabled(true);
                        mPasswordEditText.setEnabled(true);
                        mSignUpTextView.setEnabled(true);
                    }
                });


    }

    private boolean checkPassword(String password) {
        if (password.isEmpty() || !isPasswordValid(password)) {
            mPasswordEditText.setError(getString(R.string.err_msg_password));
            requestFocus(mPasswordEditText);
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        return (password.length() >= 6);
    }

    private boolean checkEmail(String email) {
        if (email.isEmpty() || !isEmailValid(email)) {
            mEmailEditText.setError(getString(R.string.err_msg_email));
            requestFocus(mEmailEditText);
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void SignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}

