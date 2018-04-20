package ru.alexander.yourlocalfriend;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.alexander.yourlocalfriend.packageDTO.Message;


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
    private RecyclerView mMessagesList;
    private  final List<Message> messageList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        mChatToolBar = (Toolbar) findViewById(R.id.chat_app_bar);
        mChatAddBtn = (ImageButton) findViewById(R.id.chat_btn_add);
        mChatSendBtn = (ImageButton) findViewById(R.id.chat_btn_send);
        mChatMessageView = (EditText) findViewById(R.id.chat_message_print);

        mAdapter = new MessageAdapter(messageList);

        mMessagesList = (RecyclerView) findViewById(R.id.messages_list);
        mLinearLayout = new LinearLayoutManager(this);

        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);

        mMessagesList.setAdapter(mAdapter);



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
        loadMessages();
    }

    private void loadMessages() {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        mRootRef.child("messages").child(mCurrentUserId).child(mChatLocalFriendId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                messageList.add(message);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

            Map commonMessageMap = new HashMap<>();
            commonMessageMap.put("message", message);
            commonMessageMap.put("seen", false);
            commonMessageMap.put("type", "text");
            commonMessageMap.put("time", ServerValue.TIMESTAMP);
            commonMessageMap.put("from", mCurrentUserId);      ;

            Map userMessageMapPush = new HashMap<>();
            userMessageMapPush.put(currentUserRef + "/" + pushId, commonMessageMap);


            //create message for Friend
            DatabaseReference friendMessagePush = mRootRef.child("FriendMessages")
                    .child(mChatLocalFriendId).child(mCurrentUserId).push();
            String friendPushId = userMessagePush.getKey();

            String currentFriendRef = "FriendMessages/" + mChatLocalFriendId + "/" + mCurrentUserId;

            //Map friendMessageMapPush = new HashMap<>();
            userMessageMapPush.put(currentFriendRef + "/" + friendPushId, commonMessageMap);

            mRootRef.updateChildren(userMessageMapPush, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.d("CHAT_LOG", databaseError.getMessage().toString());
                    }else{
                        mChatMessageView.setText("");
                    }
                }
            });
        }
    }
}
