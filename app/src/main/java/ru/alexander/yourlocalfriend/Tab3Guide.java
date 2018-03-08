package ru.alexander.yourlocalfriend;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class Tab3Guide extends  ParentFragment {
    private static final int LAYOUT=R.layout.tab3_guide;
    private static final String TAG_FRAGMENT3 ="Search";
    private String title;
    private Context context;
    OnFriendSelectedListener mCallback;
    ArrayList<YourLocalFriendDTO> LocalFriendsList;
    RecyclerView LocalFriendRV;
    private DatabaseReference  mLocalFriendDb;
    Query queryDefaultAll;
    FirebaseRecyclerAdapter<YourLocalFriendDTO, LocalFriendHolderFB> firebaseRecyclerAdapter;

    public static String getTagFragment() {
        return TAG_FRAGMENT3;
    }
    // Container Activity must implement this interface
    public interface OnFriendSelectedListener {
        public void onFriendSelected(YourLocalFriendDTO FriendObject);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = getActivity();

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (OnFriendSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_guide, container, false);

        final TextView textView1 = (TextView) rootView.findViewById(R.id.textView1);
        final TextView textView2 = (TextView) rootView.findViewById(R.id.textView2);
        EditText editTextGender = (EditText) rootView.findViewById(R.id.editTextGender);
        EditText editTextAge = (EditText) rootView.findViewById(R.id.editTextAge);
        EditText editTextHobby = (EditText) rootView.findViewById(R.id.editTextHobby);


        //Firebase UI , recycler view adapter-------------------------------------------------------
        queryDefaultAll = FirebaseDatabase.getInstance()
                .getReference()
                .child("LocalFriends");

        FirebaseRecyclerOptions<YourLocalFriendDTO> options =
                new FirebaseRecyclerOptions.Builder<YourLocalFriendDTO>()
                        .setQuery(queryDefaultAll, YourLocalFriendDTO.class)
                        .build();

        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<YourLocalFriendDTO, LocalFriendHolderFB>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull LocalFriendHolderFB holder, int position, @NonNull YourLocalFriendDTO model) {
                        holder.setName(model.getYourLocalFriendName());
                        holder.setAge(model.getYourLocalFriendAge());
                        holder.setHobbies(model.getYourLocalFriendHobbies());
                        holder.setmChatUser(model.getFriendId());
                        holder.setButton();

                    }

                    @Override
                    public LocalFriendHolderFB onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_local_friend_item, parent, false);

                        return new LocalFriendHolderFB(view);
                    }

                    @Override
                    public void onError(DatabaseError e) {
                        Context context = getActivity();
                        Toast toast=Toast.makeText(getContext(), "Error while getting data about the Local Friends", Toast.LENGTH_SHORT);
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
        //Firebase UI , recycler view adapter-------------------------------------------------------


        LocalFriendRV = (RecyclerView) rootView.findViewById(R.id.local_friend_RecyclerView);
        LocalFriendRV.setHasFixedSize(true);
        LocalFriendRV.setLayoutManager(new LinearLayoutManager(getContext()));
        //show all local friend from database
        LocalFriendRV.setAdapter(firebaseRecyclerAdapter);

        Button btnSrchEnteredParameters = (Button) rootView.findViewById(R.id.btnSrchEnteredParameters);
        Button btnSrchYourParameters = (Button) rootView.findViewById(R.id.btnSrchYourParameters);


        if (savedInstanceState==null){
            //LocalFriendsList=new ArrayList<YourLocalFriendDTO>();
        }else{
            LocalFriendsList=savedInstanceState.getParcelableArrayList("localFriendsList");
            //LocalFriendRV.setAdapter(new LocalFriendsListAdapter(LocalFriendsList,getActivity(),Tab3Guide.this));
            LocalFriendRV.setAdapter(firebaseRecyclerAdapter);
        }

        btnSrchEnteredParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                Toast toast=Toast.makeText(v.getContext(), "I am searching by ENTERED details , please wait!", Toast.LENGTH_SHORT);
                TextView text;
                View vieew = toast.getView();
                text = (TextView) vieew.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.toastTexColor));
                //text.setShadowLayer(0,0,0,0);
                text.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                vieew.setBackgroundResource(R.drawable.toast);
                toast.setView(vieew);
                toast.show();

                //generateListView();
                //LocalFriendRV.setAdapter(new LocalFriendsListAdapter(LocalFriendsList,getActivity(),Tab3Guide.this));
                //TODO create query to search by entered values
                LocalFriendRV.setAdapter(firebaseRecyclerAdapter);

            }
        });


        btnSrchYourParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context context = getActivity();
                Toast toast=Toast.makeText(v.getContext(), "I am searching FRIEND LIKE YOU , please wait!", Toast.LENGTH_SHORT);
                TextView text;
                View vieew = toast.getView();
                text = (TextView) vieew.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.toastTexColor));
                //text.setShadowLayer(0,0,0,0);
                text.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                vieew.setBackgroundResource(R.drawable.toast);
                toast.setView(vieew);
                toast.show();

                //generateListView();
                //LocalFriendRV.setAdapter(new LocalFriendsListAdapter(LocalFriendsList,getActivity(),Tab3Guide.this));
                //TODO create query to search by user parameters
                LocalFriendRV.setAdapter(firebaseRecyclerAdapter);

            }
        });
        return rootView;
    }

    /*----------------------------------------------------------------------------------------------
    //to implement  inicialization with the results from server
    //private List<YourLocalFriendDTO> generateListView() {
    private void generateListView() {
            //create a query based on entered parameters
            //sends to server
            //gets the results as ArrayList<YourLocalFriend> guides object
            //Show the ListView
        ArrayList<YourLocalFriendDTO> LocalFriendsListFromDB=new ArrayList<YourLocalFriendDTO>();
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Harper Lee", "85", "writing, living, imagination", 1));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Jean Louise", "10", "playing, school, lessons, scary stories", 2));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Jem", "11", "playing, playing , playing, playing, playing, playing", 3));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Atticus Finch", "50", "law, law, law, law, law, law, law, law, law", 4));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Calpornia", "60", "Cooking, playing, Cooking,Cooking,Cooking,Cooking", 5));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Boo Radly", "50", "Scary", 6));

        this.LocalFriendsList=LocalFriendsListFromDB;
        //return LocalFriendsList;
    }
    /*----------------------------------------------------------------------------------------------
    /*
     */
    // Send the event to the host activity
    public  void passToAnotherActivity(YourLocalFriendDTO FriendObject){
          mCallback.onFriendSelected(FriendObject);
    }

    @Override
    public void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("localFriendsList", LocalFriendsList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState==null){
            LocalFriendsList=new ArrayList<YourLocalFriendDTO>();
            LocalFriendRV.setAdapter(firebaseRecyclerAdapter);
        }else{
            LocalFriendsList=savedInstanceState.getParcelableArrayList("localFriendsList");
            //LocalFriendRV.setAdapter(new LocalFriendsListAdapter(LocalFriendsList,getActivity(),Tab3Guide.this));
            LocalFriendRV.setAdapter(firebaseRecyclerAdapter);
        }
    }
    public static Tab3Guide getInstanceTab3(Context context){
        Bundle args=new Bundle();
        Tab3Guide tab3=new Tab3Guide();
        tab3.setArguments(args);
        tab3.setContext(context);
        tab3.setTitle (context.getString(R.string.tab_item_find_friend));

        return tab3;
    }


    //Firebase UI , recycler view adapter-----------------------------------------------------------
    public static class LocalFriendHolderFB extends RecyclerView.ViewHolder {
        View mView;
        TextView name;
        TextView age;
        TextView hobby;
        Button button;

        private DatabaseReference mRootRef;
        private FirebaseAuth mAuth;
        private String mCurrentUserId;
        private String mChatUser;

        public void setmChatUser()
        {

        }

        public LocalFriendHolderFB(View itemView) {
            super(itemView);
            mView = itemView;
        }

         public void setName(String nameFromDTO){
                name = (TextView) mView.findViewById(R.id.local_friend__name_in_card_view);
                name.setText(nameFromDTO);
         }

         public void setAge(String AgeFromDTO){
            age = (TextView) mView.findViewById(R.id.local_friend_age);
            age.setText(AgeFromDTO);
         }

        public void setHobbies(String HobbiesFromDTO){
            hobby = (TextView) mView.findViewById(R.id.local_friends_hobby);
            hobby.setText(HobbiesFromDTO);
        }

        public void setmChatUser(String IDFromDTO )
        {
            mChatUser = IDFromDTO;
        }


        public void setButton(){
            button = (Button) itemView.findViewById(R.id.btn_add_to_chat);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO to add to chat
                    addToChat();
                    //passToAnotherActivity(model);
                }
            });
        }


        private void addToChat() {

            mRootRef = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            mCurrentUserId = mAuth.getCurrentUser().getUid();
            final DatabaseReference mRootRefFriendChat = mRootRef.child("LocalFriendChat").child(mChatUser);

            mRootRef.child("Chat").child(mCurrentUserId).addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(mChatUser)) {

                        Toast toast = Toast.makeText(mView.getContext(), "Your LocalFriend is already in chat.", Toast.LENGTH_SHORT);
                        TextView text;
                        View vieew = toast.getView();
                        text = (TextView) vieew.findViewById(android.R.id.message);
                        text.setTextColor(mView.getResources().getColor(R.color.toastTexColor));
                        //text.setShadowLayer(0,0,0,0);
                        text.setBackgroundColor(mView.getResources().getColor(R.color.toastBackground));
                        vieew.setBackgroundResource(R.drawable.toast);
                        toast.setView(vieew);
                        toast.show();

                    } else {
                        Map chatAddMap = new HashMap();
                        chatAddMap.put("seen", false);
                        chatAddMap.put("timestamp", new Date());

                        Map chatUserMap = new HashMap();
                        chatUserMap.put("Chat/" + mCurrentUserId + "/" + mChatUser, chatAddMap);

                        Map chatFriendMap = new HashMap();
                        chatFriendMap.put("LocalFriendChat/" + mChatUser + "/" + mCurrentUserId, chatAddMap);

                        mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                if (databaseError != null) {
                                    Log.d("CHAT_LOG", databaseError.getMessage().toString());
                                }
                            }
                        });

                        mRootRef.updateChildren(chatFriendMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                if (databaseError != null) {
                                    Log.d("LOCAL_FRIEND_CHAT_LOG", databaseError.getMessage().toString());
                                }
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
    //Firebase UI , recycler view adapter-----------------------------------------------------------


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



