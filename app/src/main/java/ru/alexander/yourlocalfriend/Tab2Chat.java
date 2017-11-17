package ru.alexander.yourlocalfriend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

/**
 * Created by ekaterina on 01/07/2017.
 */

public class Tab2Chat extends Fragment {
    private static final int LAYOUT=R.layout.tab2_chat;
    private static final int TITLE=R.string.tab_item_chats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_chat, container, false);
        //noadress chat
        //RecyclerView noAddressRV=(RecyclerView)rootView.findViewById(R.id.noadressChatRecyclerView);
        //noAddressRV.setLayoutManager(new LinearLayoutManager(getContext()));

        //adress chats
        RecyclerView AddressRV=(RecyclerView)rootView.findViewById(R.id.adressChatRecyclerView);
        AddressRV.setLayoutManager(new LinearLayoutManager(getContext()));
        //create list : List<YourLocalFriendDTO> data;
        AddressRV.setAdapter(new AdressChatListAdapter(createMockAddressChatList()));

        return rootView;
    }

    private List<YourLocalFriendDTO> createMockAddressChatList() {
        List<YourLocalFriendDTO> data=new ArrayList<YourLocalFriendDTO>();
        data.add(new YourLocalFriendDTO("Jean Louise", "10", "playing"));
        data.add(new YourLocalFriendDTO("Jem", "11", "playing"));
        data.add(new YourLocalFriendDTO("Atticus Finch", "50", "law"));
        data.add(new YourLocalFriendDTO("Calpornia", "60", "Cooking"));
        data.add(new YourLocalFriendDTO("Boo Radly", "50", "Scary"));

        return data;
    }
}
