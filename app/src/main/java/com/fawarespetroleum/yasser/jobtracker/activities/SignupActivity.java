package com.fawarespetroleum.yasser.jobtracker.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        unbinder = ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder()
                            .setDisplayName(mNameEditText.getEditableText().toString());
                    user.updateProfile(builder.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mSignUpProgressBar.setVisibility(View.GONE);
                                mSignUpButton.setEnabled(true);
                                mEmailEditText.setEnabled(true);
                                mPasswordEditText.setEnabled(true);
                                mSignInTextView.setEnabled(true);
                                mNameEditText.setEnabled(true);
                                myRef.child(user.getUid()).setValue(user);
                                finish();
                            }
                        }
                    });
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


    public void Signup(View view) {

        String email = mEmailEditText.getEditableText().toString();
        String password = mPasswordEditText.getEditableText().toString();

        if(!checkEmail(email)) {
            return;
        }
        if(!checkPassword(password)) {
            return;
        }

        mSignUpProgressBar.setVisibility(View.VISIBLE);
        mSignUpButton.setEnabled(false);
        mEmailEditText.setEnabled(false);
        mPasswordEditText.setEnabled(false);
        mSignInTextView.setEnabled(false);
        mNameEditText.setEnabled(false);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Log.d(TAG, "sign up failed");
                            mSignUpProgressBar.setVisibility(View.GONE);
                            mSignUpButton.setEnabled(true);
                            mEmailEditText.setEnabled(true);
                            mPasswordEditText.setEnabled(true);
                            mSignInTextView.setEnabled(true);
                            mNameEditText.setEnabled(true);
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(SignupActivity.this, getString(R.string.err_msg_email_collision)
                                        , Toast.LENGTH_SHORT).show();
                                requestFocus(mEmailEditText);
                            }else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(SignupActivity.this, getString(R.string.err_msg_email_malformed)
                                        , Toast.LENGTH_SHORT).show();
                                requestFocus(mEmailEditText);
                            } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(SignupActivity.this, getString(R.string.err_msg_weak_pass)
                                        , Toast.LENGTH_SHORT).show();
                                requestFocus(mPasswordEditText);
                            }
                        }else{
                            Log.d(TAG, "sign up completed");
                        }
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

    public void SignIn(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
