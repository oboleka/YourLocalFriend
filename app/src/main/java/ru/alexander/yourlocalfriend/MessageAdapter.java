package ru.alexander.yourlocalfriend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.Message;

/**
 * Created by ekaterina on 18/04/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> mMessageList;

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

        public MessageViewHolder(View view){
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_view);
            //timeText = (TextView) view.findViewById(R.id.message_item_time);
            friendImageView = (ImageView) view.findViewById(R.id.friend_message_image_view);
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder viewHolder, int i){
        Message c = mMessageList.get(i);
        viewHolder.messageText.setText(c.getMessage());
        //viewHolder.timeText.setText(c.getTime());
    }

    @Override
    public int getItemCount(){
        return mMessageList.size();
    }
}
