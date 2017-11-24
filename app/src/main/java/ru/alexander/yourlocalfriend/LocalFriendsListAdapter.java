package ru.alexander.yourlocalfriend;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class LocalFriendsListAdapter extends RecyclerView.Adapter<LocalFriendsListAdapter.LocalFriendHolder> {

    private List<YourLocalFriendDTO> LocalFriendsList;

    public LocalFriendsListAdapter(List<YourLocalFriendDTO> data){
        this.LocalFriendsList=data;
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
    public void onBindViewHolder(LocalFriendHolder holder, int position) {
        holder.name.setText(LocalFriendsList.get(position).getYourLocalFriendName());
        holder.age.setText(LocalFriendsList.get(position).getYourLocalFriendAge());
        holder.hobby.setText(LocalFriendsList.get(position).getYourLocalFriendHobbies());
    }

    @Override
    public int getItemCount() {
        return LocalFriendsList.size();
    }

    public static class LocalFriendHolder extends RecyclerView.ViewHolder {
         CardView cardView;
         TextView name;
         TextView age;
         TextView hobby;

         public LocalFriendHolder(View itemView) {
             super(itemView);

             cardView=(CardView)itemView.findViewById(R.id.your_local_friend_card_view);
             name =(TextView) itemView.findViewById(R.id.local_friend__name_in_card_view);
             age =(TextView) itemView.findViewById(R.id.local_friend_age);
             hobby=(TextView) itemView.findViewById(R.id.local_friends_hobby);
         }
     }

    ;
}
