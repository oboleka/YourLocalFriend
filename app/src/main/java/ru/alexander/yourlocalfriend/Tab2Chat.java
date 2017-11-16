package ru.alexander.yourlocalfriend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return rootView;
    }
}
