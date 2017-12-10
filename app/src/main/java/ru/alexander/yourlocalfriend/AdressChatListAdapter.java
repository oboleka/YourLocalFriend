package ru.alexander.yourlocalfriend;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class AdressChatListAdapter extends RecyclerView.Adapter<AdressChatListAdapter.AdressChatHolder> {

    public List<YourLocalFriendDTO> data1;

    public AdressChatListAdapter(List<YourLocalFriendDTO> data){
        data1=new ArrayList<YourLocalFriendDTO>();
        this.data1=data;
    }

    @Override
    public AdressChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.adress_chat_item, parent, false) ;

        return new AdressChatHolder(view);
    }
    // set values to chat item
    @Override
    public void onBindViewHolder(AdressChatHolder holder, int position) {
        holder.name.setText(data1.get(position).getYourLocalFriendName());
    }

    @Override
    public int getItemCount() {
        return data1.size();

    }

    public void setData(List<YourLocalFriendDTO> data) {
        this.data1 = data;
    }

    public static class AdressChatHolder extends RecyclerView.ViewHolder {
         CardView cardView;
         TextView name;

         public AdressChatHolder(View itemView) {
             super(itemView);

             cardView=(CardView)itemView.findViewById(R.id.adress_chat_cardView);
             name =(TextView) itemView.findViewById(R.id.chat_friend_name_in_card_view);
         }
     }

    ;
}
