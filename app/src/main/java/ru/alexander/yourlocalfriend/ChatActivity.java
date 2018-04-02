package ru.alexander.yourlocalfriend;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ChatActivity extends AppCompatActivity {

    private String mChatLocalFriendId;
    private String mChatLocalFriendName;
    private Toolbar mChatToolBar;
    private ImageButton mChatSendBtn;
    private ImageButton mChatAddBtn;
    private EditText mChatMessageView;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;
    Query queryAllChats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        mChatToolBar = (Toolbar) findViewById(R.id.chat_app_bar);
        mChatAddBtn = (ImageButton) findViewById(R.id.chat_btn_add);
        mChatSendBtn = (ImageButton) findViewById(R.id.chat_btn_send);
        mChatMessageView = (EditText) findViewById(R.id.chat_message_print);

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

        // send button actions
        mChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    private void sendMessage() {

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        queryAllChats = FirebaseDatabase.getInstance().getReference().child("Chat").child(mCurrentUserId);

        String message = mChatMessageView.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            //create message for User
            String currentUserRef = "messages/" + mCurrentUserId + "/" + mChatLocalFriendId;

            DatabaseReference userMessagePush = mRootRef.child("messages")
                    .child(mCurrentUserId).child(mChatLocalFriendId).push();
            String pushId = userMessagePush.getKey();

            Map userMessageMap = new HashMap<>();
            userMessageMap.put("message", message);
            userMessageMap.put("seen", false);
            userMessageMap.put("type", "text");
            userMessageMap.put("time", ServerValue.TIMESTAMP);

            Map userMessageMapPush = new HashMap<>();
            userMessageMapPush.put(currentUserRef + "/" + pushId, userMessageMap);


            //create message for Friend

            DatabaseReference friendMessagePush = mRootRef.child("FriendMessages")
                    .child(mChatLocalFriendId).child(mCurrentUserId).push();
            String friendPushId = userMessagePush.getKey();

            String currentFriendRef = "FriendMessages/" + mChatLocalFriendId + "/" + mCurrentUserId;

            //Map friendMessageMapPush = new HashMap<>();
            userMessageMapPush.put(currentFriendRef + "/" + friendPushId, userMessageMap);

            mRootRef.updateChildren(userMessageMapPush, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.d("CHAT_LOG", databaseError.getMessage().toString());
                    }
                }
            });
        }
    }
}
