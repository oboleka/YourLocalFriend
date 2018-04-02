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
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout mDisplayName;
    TextInputLayout mEmail;
    TextInputLayout mPassword;
    private Button mCreateBtn;
    private Toolbar mToolBar;
    private ProgressDialog mRegProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private DatabaseReference mRootRef;
    private DatabaseReference mDataBaseLocalFriends;
    private String mCurrentUserId;

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

    private void register_user(final String display_name, final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(  , "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mRegProgress.dismiss();

                            initiateRegisteredUser(display_name, email, password);

                            //to be deleted when data will really appear in db
                            initiateLocalFriends();

                            Intent mainIntent=new Intent(RegisterActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ResisterActivity", "createUserWithEmail:failure", task.getException());
                            mRegProgress.hide();
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    //to be deleted when data will really appear in db
    private void initiateLocalFriends() {
        mDataBaseLocalFriends = FirebaseDatabase.getInstance().getReference().child("LocalFriends");
        Map LocalFriendsListToDB = new HashMap<>();
        LocalFriendsListToDB.put("LocalFriends/"+"0", new YourLocalFriendDTO("Harper Lee", "85", "writing, living, imagination", "0"));
        LocalFriendsListToDB.put("LocalFriends/"+"2", new YourLocalFriendDTO("Jean Louise", "10", "playing, school, lessons, scary stories", "2"));
        LocalFriendsListToDB.put("LocalFriends/"+"3", new YourLocalFriendDTO("Jem", "11", "playing, playing , playing, playing, playing, playing", "3"));
        LocalFriendsListToDB.put("LocalFriends/"+"4", new YourLocalFriendDTO("Atticus Finch", "50", "law, law, law, law, law, law, law, law, law", "4"));
        LocalFriendsListToDB.put("LocalFriends/"+"5", new YourLocalFriendDTO("Calpornia", "60", "Cooking, playing, Cooking,Cooking,Cooking,Cooking", "5"));
        LocalFriendsListToDB.put("LocalFriends/"+"6", new YourLocalFriendDTO("Boo Radly", "50", "Scary", "6"));
        LocalFriendsListToDB.put("LocalFriends/"+"7", new YourLocalFriendDTO("NOADRESS CHAT", "50", "Scary", "7"));

        mRootRef = FirebaseDatabase.getInstance().getReference();
        //add chat to users chats
        mRootRef.updateChildren(LocalFriendsListToDB, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if (databaseError != null) {
                                Log.d("CHAT_LOG", databaseError.getMessage().toString());
                            }
                        }
                    });



    }

    private void initiateRegisteredUser(String display_name, String email, String password) {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();

        //create User record
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDataBase = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
        HashMap<String, String> userRegMap = new HashMap<>();
        userRegMap.put("displayName", display_name);
        userRegMap.put("email", email);
        userRegMap.put("password", password);
        mDataBase.setValue(userRegMap);


        final DatabaseReference mRootRefFriendChat = mRootRef.child("LocalFriendChat").child("7");

        //add NO ADDRESS chat to users chats
        Map chatAddMap = new HashMap();
        chatAddMap.put("seen", false);
        chatAddMap.put("timestamp", ServerValue.TIMESTAMP);

        Map chatUserMap = new HashMap();
        chatUserMap.put("Chat/" + mCurrentUserId + "/" + "7", chatAddMap);


         mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d("CHAT_LOG", databaseError.getMessage().toString());
                }
            }
         });


        //--create chat to NO ADDRESS CHAT LocalFriend
         Map chatFriendMap = new HashMap();
         chatFriendMap.put("LocalFriendChat/" + "7" + "/" + mCurrentUserId, chatAddMap);

         mRootRef.updateChildren(chatFriendMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError != null) {
                    Log.d("CHAT_LOG", databaseError.getMessage().toString());
                }
            }
         });
    }
}
