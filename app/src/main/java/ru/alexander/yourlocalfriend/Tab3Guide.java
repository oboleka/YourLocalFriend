package ru.alexander.yourlocalfriend;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class Tab3Guide extends  ParentFragment {
    private static final int LAYOUT=R.layout.tab3_guide;
    private String title;
    private Context context;
    OnFriendSelectedListener mCallback;
    public ArrayList<YourLocalFriendDTO> LocalFriendsList;
    RecyclerView LocalFriendRV;

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


        LocalFriendRV=(RecyclerView)rootView.findViewById(R.id.local_friend_RecyclerView);
        LocalFriendRV.setLayoutManager(new LinearLayoutManager(getContext()));


        Button btnSrchEnteredParameters = (Button) rootView.findViewById(R.id.btnSrchEnteredParameters);
        Button btnSrchYourParameters = (Button) rootView.findViewById(R.id.btnSrchYourParameters);


        if (savedInstanceState==null){
            LocalFriendsList=new ArrayList<YourLocalFriendDTO>();
        }else{
            LocalFriendsList=savedInstanceState.getParcelableArrayList("localFriendsList");
            LocalFriendRV.setAdapter(new LocalFriendsListAdapter(LocalFriendsList,getActivity(),Tab3Guide.this));
        }

        btnSrchEnteredParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                //Toast.makeText(context, "I am searching by SELECTED parameters , please wait!", Toast.LENGTH_SHORT);
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

                generateListView();
                LocalFriendRV.setAdapter(new LocalFriendsListAdapter(LocalFriendsList,getActivity(),Tab3Guide.this));

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

                generateListView();
                LocalFriendRV.setAdapter(new LocalFriendsListAdapter(LocalFriendsList,getActivity(),Tab3Guide.this));


            }
        });
        return rootView;
    }

    //to implement  inicialization with the results from server
    //private List<YourLocalFriendDTO> generateListView() {
    private void generateListView() {
            //create a query based on entered parameters
            //sends to server
            //gets the results as ArrayList<YourLocalFriend> guides object
            //Show the ListView
        ArrayList<YourLocalFriendDTO> LocalFriendsListFromDB=new ArrayList<YourLocalFriendDTO>();
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Harper Lee", "85", "writing, living, imagination"));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Jean Louise", "10", "playing, school, lessons, scary stories"));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Jem", "11", "playing, playing , playing, playing, playing, playing"));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Atticus Finch", "50", "law, law, law, law, law, law, law, law, law"));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Calpornia", "60", "Cooking, playing, Cooking,Cooking,Cooking,Cooking"));
        LocalFriendsListFromDB.add(new YourLocalFriendDTO("Boo Radly", "50", "Scary"));

        this.LocalFriendsList=LocalFriendsListFromDB;
        //return LocalFriendsList;
    }

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            LocalFriendsList=new ArrayList<YourLocalFriendDTO>();
        }else{
            LocalFriendsList=savedInstanceState.getParcelableArrayList("localFriendsList");
            LocalFriendRV.setAdapter(new LocalFriendsListAdapter(LocalFriendsList,getActivity(),Tab3Guide.this));
        }
    }

    public static Tab3Guide getInstanceTab3(Context context){
        Bundle args=new Bundle();
        Tab3Guide tab3=new Tab3Guide();
        tab3.setArguments(args);
        tab3.setContext(context);
        tab3.setTitle (context.getString(R.string.tab_item_find_friend));

        return tab3;
        //updateddata=new ArrayList<YourLocalFriendDTO>();
        //updateddata.add(new YourLocalFriendDTO("INIT", "15", "plat" ));
    }
}



