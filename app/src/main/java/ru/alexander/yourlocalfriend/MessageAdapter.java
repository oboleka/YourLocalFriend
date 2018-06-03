package ru.alexander.yourlocalfriend;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.Message;

/**
 * Created by ekaterina on 18/04/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> mMessageList;
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef ;

    public MessageAdapter(List<Message> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);

    }


    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView messageText;
        public TextView timeText;
        public ImageView friendImageView;
        public TextView name;

        public MessageViewHolder(View view){
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text);
            timeText = (TextView) view.findViewById(R.id.message_time);
            name = (TextView) view.findViewById(R.id.message_name);
            friendImageView = (ImageView) view.findViewById(R.id.friend_message_image_view);
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i){

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();
        mRootRef = FirebaseDatabase.getInstance().getReference();

        Message c = mMessageList.get(i);
        String fromUser = c.getFrom();
        if (fromUser.equals(currentUserId)){
            viewHolder.messageText.setBackgroundColor(Color.WHITE);
            viewHolder.messageText.setTextColor(Color.BLACK);
            viewHolder.name.setText("You:");
        }else{
            viewHolder.messageText.setBackgroundColor(Color.LTGRAY);
            viewHolder.messageText.setTextColor(Color.BLACK);
            mRootRef.child("LocalFriends").child(c.getFrom()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String mChatLocalFriendName = dataSnapshot.child("yourLocalFriendName").getValue().toString();
                    viewHolder.name.setText(mChatLocalFriendName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
        viewHolder.messageText.setText(c.getMessage());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:MM:SS");
        viewHolder.timeText.setText(sdf.format(new Date(Long.valueOf(c.getTime()))));
    }

    @Override
    public int getItemCount(){
        return mMessageList.size();
    }
}
