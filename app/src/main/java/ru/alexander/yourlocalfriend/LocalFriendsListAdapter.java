package ru.alexander.yourlocalfriend;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class LocalFriendsListAdapter extends RecyclerView.Adapter<LocalFriendsListAdapter.LocalFriendHolder> {

    private List<YourLocalFriendDTO> LocalFriendsList;
    Context mContext;
    Fragment fragment;

    public LocalFriendsListAdapter(List<YourLocalFriendDTO> data, Context mContext, Fragment fragment){
        this.LocalFriendsList=data;
        this.mContext = mContext;
        this.fragment=fragment;

    }

    public LocalFriendsListAdapter(){
        super();
    }

    @Override
    public LocalFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.your_local_friend_item, parent, false) ;

        return new LocalFriendHolder(view);
    }

    // set values to chat item
    @Override
    public void onBindViewHolder(final LocalFriendHolder holder, final int position) {
        holder.name.setText(LocalFriendsList.get(position).getYourLocalFriendName());
        holder.age.setText(LocalFriendsList.get(position).getYourLocalFriendAge());
        holder.hobby.setText(LocalFriendsList.get(position).getYourLocalFriendHobbies());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  YourLocalFriendDTO FriendToChat =LocalFriendsList.get(position) ;
                ((Tab3Guide) fragment).passToAnotherActivity(FriendToChat);
            }
        });

    }

    @Override
    public int getItemCount() {
        return LocalFriendsList.size();
    }

    //Working Holder--------------------------------
    public static class LocalFriendHolder extends RecyclerView.ViewHolder {
         CardView cardView;
         TextView name;
         TextView age;
         TextView hobby;
         Button button;

         public LocalFriendHolder(View itemView) {
             super(itemView);

             cardView=(CardView)itemView.findViewById(R.id.your_local_friend_card_view);
             name =(TextView) itemView.findViewById(R.id.local_friend__name_in_card_view);
             age =(TextView) itemView.findViewById(R.id.local_friend_age);
             hobby=(TextView) itemView.findViewById(R.id.local_friends_hobby);
             button=(Button) itemView.findViewById(R.id.btn_add_to_chat);


         }
     }
    //Working Holder--------------------------------

}
