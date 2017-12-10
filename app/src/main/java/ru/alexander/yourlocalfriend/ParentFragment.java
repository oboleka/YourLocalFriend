package ru.alexander.yourlocalfriend;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

/**
 * Created by ekaterina on 30/11/2017.
 */

public class ParentFragment extends Fragment {
    //for data base search results
    public List<YourLocalFriendDTO> LocalFriendsList=new ArrayList<YourLocalFriendDTO>();
    //for chat list
    public List<YourLocalFriendDTO> data;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
