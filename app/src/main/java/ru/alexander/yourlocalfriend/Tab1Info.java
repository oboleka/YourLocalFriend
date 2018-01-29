package ru.alexander.yourlocalfriend;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;

public class Tab1Info extends ParentFragment {
    private static final int LAYOUT=R.layout.tab1_info;
    private static final String TAG_FRAGMENT1 ="UserInfo" ;


    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public static String getTagFragment() {
        return TAG_FRAGMENT1;
    }


    public static Tab1Info getInstanceTab1Info(Context context){

        Bundle args=new Bundle();
        Tab1Info tab1=new Tab1Info();
        tab1.setArguments(args);
        tab1.setContext(context);
        tab1.setTitle(context.getString(R.string.tab_item_your_details));
        return tab1;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_info, container, false);
        return rootView;
    }



}
