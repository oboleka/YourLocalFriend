package ru.alexander.yourlocalfriend;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

public class Tab3Guide extends Fragment {
    private static final int LAYOUT=R.layout.tab3_guide;
    private static final int TITLE=R.string.tab_item_find_friend;
    public List<YourLocalFriendDTO> LocalFriendsList=new ArrayList<YourLocalFriendDTO>();

    //ArrayList<String> your_array_list=new ArrayList<String>();
    //ListView localFriendsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_guide, container, false);
        final TextView textView1 = (TextView) rootView.findViewById(R.id.textView1);
        final TextView textView2 = (TextView) rootView.findViewById(R.id.textView2);
        EditText editTextGender = (EditText) rootView.findViewById(R.id.editTextGender);
        EditText editTextAge = (EditText) rootView.findViewById(R.id.editTextAge);
        EditText editTextHobby = (EditText) rootView.findViewById(R.id.editTextHobby);


        final RecyclerView LocalFriendRV=(RecyclerView)rootView.findViewById(R.id.local_friend_RecyclerView);
        LocalFriendRV.setLayoutManager(new LinearLayoutManager(getContext()));
        //create list : List<YourLocalFriendDTO> LocalFriendsList;


        Button btnSrchEnteredParameters = (Button) rootView.findViewById(R.id.btnSrchEnteredParameters);
        Button btnSrchYourParameters = (Button) rootView.findViewById(R.id.btnSrchYourParameters);

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

                LocalFriendRV.setAdapter(new LocalFriendsListAdapter(generateListView()));



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

                LocalFriendRV.setAdapter(new LocalFriendsListAdapter(generateListView()));


            }
        });



        return rootView;
    }
    //to implement  inicialization with the results from server
    private List<YourLocalFriendDTO> generateListView() {
            //create a query based on entered parameters
            //sends to server
            //gets the results as ArrayList<YourLocalFriend> guides object
            //Show the ListView
        LocalFriendsList.add(new YourLocalFriendDTO("NO ADDRESS CHAT", "10", "playing"));
        LocalFriendsList.add(new YourLocalFriendDTO("Jean Louise", "10", "playing"));
        LocalFriendsList.add(new YourLocalFriendDTO("Jem", "11", "playing"));
        LocalFriendsList.add(new YourLocalFriendDTO("Atticus Finch", "50", "law"));
        LocalFriendsList.add(new YourLocalFriendDTO("Calpornia", "60", "Cooking"));
        LocalFriendsList.add(new YourLocalFriendDTO("Boo Radly", "50", "Scary"));

        return LocalFriendsList;
        }
    }
