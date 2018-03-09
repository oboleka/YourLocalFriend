package ru.alexander.yourlocalfriend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.Chat;
import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class Tab2Chat extends ParentFragment {
    private static final int LAYOUT=R.layout.tab2_chat;
    private final static String TAG_FRAGMENT2 = "ChatsFragment";
    private String title;
    private Context context;
    static ArrayList<YourLocalFriendDTO> updateddata;
    RecyclerView AddressRV;
    private static AdressChatListAdapter  adapter;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;


    Query queryAllChats;
    FirebaseRecyclerAdapter<Chat, Tab2Chat.ChatsHolderFB> firebaseRecyclerAdapter;


    public static String getTagFragment() {
        return TAG_FRAGMENT2;
    }

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
        adapter=new AdressChatListAdapter(updateddata);
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
        AddressRV.setHasFixedSize(true);
        AddressRV.setLayoutManager(new LinearLayoutManager(getContext()));
        //adapter=new AdressChatListAdapter(updateddata);
        //AddressRV.setAdapter(adapter);

        //------------------------------------------------------------------------------------------------
        //Firebase UI , recycler view adapter-------------------------------------------------------
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        queryAllChats = FirebaseDatabase.getInstance().getReference().child("Chat").child(mCurrentUserId);

        //queryAllChats.addValueEventListener(new)
        FirebaseRecyclerOptions<Chat> options =
                new FirebaseRecyclerOptions.Builder<Chat>()
                        .setQuery(queryAllChats, Chat.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Chat, Tab2Chat.ChatsHolderFB>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Tab2Chat.ChatsHolderFB holder, int position, @NonNull Chat model) {
                holder.setName(model.getTimestamp().toString());
            }

            @Override
            public Tab2Chat.ChatsHolderFB onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adress_chat_item, parent, false);

                return new ChatsHolderFB(view);
            }
            @Override
            public void onError(DatabaseError e) {
            Context context = getActivity();
            Toast toast=Toast.makeText(getContext(), "Error while getting data about the Local Friends Chats", Toast.LENGTH_SHORT);
            TextView text;
            View vieew = toast.getView();
            text = (TextView) vieew.findViewById(android.R.id.message);
                        text.setTextColor(getResources().getColor(R.color.toastTexColor));
            //text.setShadowLayer(0,0,0,0);
                        text.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                        vieew.setBackgroundResource(R.drawable.toast);
                        toast.setView(vieew);
                        toast.show();
        }
        };



        AddressRV.setAdapter(firebaseRecyclerAdapter);

        return rootView;
    }

    @Override
    public  void onResume(){
        super.onResume();

    }
    public void refreshData(List<YourLocalFriendDTO> list){
        //if (adapter!=null){
            adapter.setData(list);
            adapter.notifyDataSetChanged();
        //}
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //----------------------------------------------------------------------------------------------
    //chat view holder


    public class ChatsHolderFB extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView name;

        public ChatsHolderFB(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.adress_chat_cardView);
            name = (TextView) itemView.findViewById(R.id.chat_friend_name_in_card_view);
        }

        public void setName(String id) {
            this.name.setText(id);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
}
