package ru.alexander.yourlocalfriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

import static ru.alexander.yourlocalfriend.R.color.toastBackground;
import static ru.alexander.yourlocalfriend.R.color.toastTexColor;


public class MyAdapter extends ArrayAdapter<String> {
    private int layout;

    public MyAdapter(Context context, int resources, ArrayList<String> objects){
        super(context, resources, objects);
        layout=resources;
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent){
    ViewHolder mainViewHolder=null;

        if (convertView==null){

        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView=inflater.inflate(layout, parent, false);
        ViewHolder viewHolder=new ViewHolder();


        viewHolder.listImageView=(ImageView) convertView.findViewById(R.id.list_view_image);
        viewHolder.title=(TextView) convertView.findViewById(R.id.list_view_text_view1);
        //viewHolder.textView2=(TextView) convertView.findViewById(R.id.list_view_text_view2);
       // viewHolder.textView3=(TextView) convertView.findViewById(R.id.list_view_text_view3);
        viewHolder.btnAddtoChat=(Button) convertView.findViewById(R.id.btn_add_to_chat);
        viewHolder.btnAddtoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast=new Toast(getContext(), "Friend is added to chat: position: " + position , Toast.LENGTH_SHORT);
                //toast.makeText(getContext(), "Friend is added to chat: position: " + position , Toast.LENGTH_SHORT).show();
                Toast toast=Toast.makeText(v.getContext(), "Friend is added to chat: position: " + position, Toast.LENGTH_SHORT);
                TextView text;
                View vieew = toast.getView();
                text = (TextView) vieew.findViewById(android.R.id.message);
                 vieew.setBackgroundResource(R.drawable.toast);
                toast.setView(vieew);
                toast.show();

                //addMockAddressChatList(YourLocalFriendDTO addedFriend);
            }
        });

        convertView.setTag(viewHolder);
        viewHolder.title.setText(getItem(position).toString());

        }else{

        mainViewHolder=(ViewHolder) convertView.getTag();
        mainViewHolder.title.setText(getItem(position).toString());

        }
    return convertView; //super.getView(position, convertView, parent);
    }
}
