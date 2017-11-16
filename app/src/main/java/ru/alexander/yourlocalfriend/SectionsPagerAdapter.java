package ru.alexander.yourlocalfriend;

import android.content.Context;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

import ru.alexander.yourlocalfriend.R.*;

/**
 *
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter{

    private Map<Integer, String> tabs;
    FragmentManager fm;
    private Context context;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        /*
        tabs=new String[]{
                "YOUR DETAILS",
                "CHATS",
                "FIND LOCAL FRIEND"
        };
        */
        this.context=context;
        tabs=new HashMap<>();
        tabs.put(0, context.getString(string.tab_item_your_details));
        tabs.put(1, context.getString(string.tab_item_chats));
        tabs.put(2, context.getString(string.tab_item_find_friend));
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // return PlaceholderFragment.newInstance(position + 1);

        //Returning the current tab
        switch (position){
            case 0:
                Tab1Info tab1=new Tab1Info();
                return tab1;
            case 1:
                Tab2Chat tab2=new Tab2Chat();
                return tab2;
            case 2:
                Tab3Guide tab3=new Tab3Guide();
                return tab3;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        /*
        switch (position) {
            case 0:
                return "YOUR DETAILS";
            case 1:
                return "CHATS";
            case 2:
                return "FIND LOCAL FRIEND";
        }
        return null;
        */
        return tabs.get(position);
    }


    public void switchToFragment(Fragment newFrag) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(id.container, newFrag);
        transaction.commit();
    }

}
