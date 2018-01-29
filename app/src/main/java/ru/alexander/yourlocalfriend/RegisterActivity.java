package ru.alexander.yourlocalfriend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout mDisplayName;
    TextInputLayout mEmail;
    TextInputLayout mPassword;
    private Button mCreateBtn;
    private Toolbar mToolBar;
    private ProgressDialog mRegProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.activity_register);

        mRegProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mToolBar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setTitle("Create account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mDisplayName=(TextInputLayout) findViewById(R.id.reg_display_input);
        mEmail=(TextInputLayout) findViewById(R.id.reg_email_input);
        mPassword=(TextInputLayout) findViewById(R.id.reg_password_input);
        mCreateBtn=(Button) findViewById(R.id.reg_submit_btn);

        mCreateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String display_name=mDisplayName.getEditText().getText().toString();
                String email=mEmail.getEditText().getText().toString();
                String password=mPassword.getEditText().getText().toString();

                if (TextUtils.isEmpty(display_name)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)) {
                }else{
                    mRegProgress.setTitle("Registering user");
                    mRegProgress.setMessage("Please wait while account is created!");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    register_user(display_name, email, password);
                }

            }
        });
    }

    private void register_user(String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(  , "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mRegProgress.dismiss();
                            Intent mainIntent=new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            mRegProgress.hide();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
