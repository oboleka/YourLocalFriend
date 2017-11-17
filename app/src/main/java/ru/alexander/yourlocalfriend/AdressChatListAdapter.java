package ru.alexander.yourlocalfriend;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class AdressChatListAdapter extends RecyclerView.Adapter<AdressChatListAdapter.AdressChatHolder> {

    private List<YourLocalFriendDTO> data;

    public AdressChatListAdapter(List<YourLocalFriendDTO> data){
        this.data=data;
    }

    @Override
    public AdressChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.adress_chat_item, parent, false) ;

        return new AdressChatHolder(view);
    }
    // set values to chat item
    @Override
    public void onBindViewHolder(AdressChatHolder holder, int position) {
        holder.name.setText(data.get(position).getYourLocalFriendName());
    }

    @Override
    public int getItemCount() {
        return data.size();
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
