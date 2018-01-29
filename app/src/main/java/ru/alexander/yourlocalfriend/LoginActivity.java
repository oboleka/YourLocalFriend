package ru.alexander.yourlocalfriend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static ru.alexander.yourlocalfriend.R.id.login_toolbar;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;
    private Button mLoginBtn;
    private ProgressDialog mLogingProgress;
    private FirebaseAuth mAuth;

    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolBar=(Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Login");

        mAuth = FirebaseAuth.getInstance();

        mLogingProgress = new ProgressDialog(this);

        mLoginEmail = (TextInputLayout) findViewById(R.id.login_email_input);
        mLoginPassword = (TextInputLayout) findViewById(R.id.login_password_input);
        mLoginBtn = (Button) findViewById(R.id.login_btn);

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = mLoginEmail.getEditText().getText().toString();
                String password = mLoginPassword.getEditText().getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Smth went wrong email:='"+email+ "', password='"+password+"'" ,
                            Toast.LENGTH_SHORT).show();
                }else {
                    ;mLogingProgress.setTitle("Login in ");
                    mLogingProgress.setMessage("Please wait for logging in ");
                    mLogingProgress.setCanceledOnTouchOutside(false);
                    mLogingProgress.show();
                    loginUser(email, password);
                }

            }
        });

    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mLogingProgress.dismiss();
                    Intent mainIntent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else{
                    mLogingProgress.hide();
                    Toast.makeText(LoginActivity.this, "Login failed. Please check the form and try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
