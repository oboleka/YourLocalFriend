package ru.alexander.yourlocalfriend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class Tab2Chat extends ParentFragment {
    private static final int LAYOUT=R.layout.tab2_chat;
    private String title;
    private Context context;
    ArrayList<YourLocalFriendDTO> updateddata;
    RecyclerView AddressRV;
    AdressChatListAdapter adapter;


    public void setUpdateddata(ArrayList<YourLocalFriendDTO> data) {
        updateddata=new ArrayList<YourLocalFriendDTO>();
        this.updateddata = data;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<YourLocalFriendDTO> getData() {
        return this.updateddata;
    }

    public static Tab2Chat getInstanceTab2Chat(Context context, ArrayList<YourLocalFriendDTO>  data ){
        Bundle args=new Bundle();
        Tab2Chat tab2=new Tab2Chat();
        tab2.setArguments(args);

        tab2.setUpdateddata(data);
        tab2.setContext(context);
        tab2.setTitle(context.getString(R.string.tab_item_chats));
        return tab2;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("updateddata", updateddata);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){

        }else {
            updateddata = savedInstanceState.getParcelableArrayList("updateddata");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_chat, container, false);

        RecyclerView AddressRV=(RecyclerView)rootView.findViewById(R.id.adressChatRecyclerView);
        AddressRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new AdressChatListAdapter(updateddata);
        AddressRV.setAdapter(adapter);
        return rootView;
    }


    @Override
    public  void onResume(){
        super.onResume();

    }
    public void refreshData(List<YourLocalFriendDTO> list){
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //View rootView = inflater.inflate(R.layout.tab2_chat, container, false);

        //RecyclerView AddressRV=(RecyclerView)rootView.findViewById(R.id.adressChatRecyclerView);
        //AddressRV.setLayoutManager(new LinearLayoutManager(getContext()));
        //adapter=new AdressChatListAdapter(updateddata);
        //AddressRV.setAdapter(adapter);
        //return rootView;
    }
}
