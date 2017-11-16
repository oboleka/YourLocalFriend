package ru.alexander.yourlocalfriend;

import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;

public class Tab1Info extends Fragment {
    private static final int LAYOUT=R.layout.tab1_info;
    private static final int TITLE=R.string.tab_item_your_details;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_info, container, false);
        return rootView;
    }



}
