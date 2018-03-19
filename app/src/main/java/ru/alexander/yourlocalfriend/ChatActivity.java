package ru.alexander.yourlocalfriend;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChatActivity extends AppCompatActivity {

    private String mChatLocalFriendId;
    private String mChatLocalFriendName;
    private Toolbar mChatToolBar;
    private DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatToolBar = (Toolbar) findViewById(R.id.chat_app_bar);

        setSupportActionBar(mChatToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);



        mChatLocalFriendId = getIntent().getStringExtra("LocalFriendId");
        mChatLocalFriendName = getIntent().getStringExtra("LocalFriendName");

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("LocalFriends").child(mChatLocalFriendId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 String mChatLocalFriendName = dataSnapshot.child("yourLocalFriendName").getValue().toString();
                getSupportActionBar().setTitle(mChatLocalFriendName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
