package ru.alexander.yourlocalfriend;

import android.content.Context;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.alexander.yourlocalfriend.R.*;
import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;


public class SectionsPagerAdapter extends FragmentPagerAdapter{

    private Map<Integer, ParentFragment> tabs;
    FragmentManager fm;
    private Context context;
    Tab2Chat tab2Chat;
    private List<YourLocalFriendDTO> data;

    public List<YourLocalFriendDTO> setData(List<YourLocalFriendDTO> data) {
        this.data = data;
        return data;
    }

    public void refreshData(List<YourLocalFriendDTO> chatlist) {
        tab2Chat.refreshData(data);
    }


    public SectionsPagerAdapter(Context context, FragmentManager fm, List<YourLocalFriendDTO> list) {
        super(fm);
        this.context=context;
        //this.data=setData(list);
        this.data=list;
        initMap(context);


    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // return PlaceholderFragment.newInstance(position + 1);

        //Returning the current tab
        /*
        switch (position){
            case 0:
                Tab1Info tab1=new Tab1Info();

                return tab1;
            case 1:
                //Tab2Chat tab2=new Tab2Chat();
                //tab2.setArguments(bundle);

                return Tab2Chat.getInstanceTab2Chat();
            case 2:
                //Tab3Guide tab3=new Tab3Guide();

                return Tab3Guide.getInstanceTab3();
            default:
                return null;

        }
        */
        return tabs.get(position);
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
        return tabs.get(position).getTitle();
    }


    public void switchToFragment(Fragment newFrag) {

        FragmentTransaction transaction = this.fm.beginTransaction();
        transaction.replace(id.container, newFrag);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    /*    @Override
    public Object instantiateItem(ViewGroup container, int position){
              Object obj=super.instantiateItem(container, position);
              if (obj instanceof Fragment){
                  Fragment f= (Fragment) obj;
                  Integer id=f.getId();
                  mFragmentTags.put(position, id);
              }
              return  obj;
      }

    public Fragment getFragment(int position){
        Integer tag=mFragmentTags.get(position);
        if (tag==null){
            return null;

        }
        return fm.findFragmentById(tag);
    }
*/
        private void initMap(Context context) {
            tabs = new HashMap<>();
            tabs.put(0, Tab1Info.getInstanceTab1Info(context));
            tab2Chat=Tab2Chat.getInstanceTab2Chat(context, data);
            tabs.put(1, tab2Chat);
            tabs.put(2, Tab3Guide.getInstanceTab3(context));
        }


}
