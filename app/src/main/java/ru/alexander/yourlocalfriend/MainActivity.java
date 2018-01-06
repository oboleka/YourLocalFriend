package ru.alexander.yourlocalfriend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ru.alexander.yourlocalfriend.packageDTO.YourLocalFriendDTO;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity  implements Tab3Guide.OnFriendSelectedListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    ArrayList<YourLocalFriendDTO> chatlist;

    public void initchatlist(){
        chatlist=new ArrayList<YourLocalFriendDTO>();
        chatlist.add(new YourLocalFriendDTO("NO ADDRESS CHAT", "date", "last", 0));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("chatlist", chatlist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState!=null){
            chatlist = savedInstanceState.getParcelableArrayList("chatlist");
        }else{
            this.initchatlist();
        }
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), chatlist);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.setData(chatlist);
/*
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment=((SectionsPagerAdapter) mViewPager.getAdapter()).getFragment(position);
                if ((position==1)&&(fragment!=null))
                {
                    //fragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

*/
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /** For the first app run asks to enter the  user info
         * if OK pressed leads to tab1
         * if NO pressed leads to tab2
         */
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isfirstrun", true);

        if (isFirstRun) {

            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Hello!");
            alert.setMessage(R.string.hello_text);
            alert.setIcon(R.drawable.hello);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mViewPager.setCurrentItem(0);
                }
            });
            alert.setNegativeButton("LATER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mViewPager.setCurrentItem(1);
                }
            });
            alert.show();
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isfirstrun", false).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFriendSelected(YourLocalFriendDTO FriendObject) {
       if (isNotAddedToChat(FriendObject)){
           chatlist.add(FriendObject);
           mSectionsPagerAdapter.setData(chatlist);
           mSectionsPagerAdapter.refreshData(chatlist);

           Toast toast=Toast.makeText(this, "Your friend is added to chats: "+FriendObject.getYourLocalFriendName(), Toast.LENGTH_SHORT);
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

    }

    public boolean isNotAddedToChat(YourLocalFriendDTO FriendObject) {
        for (int i = 0; i < chatlist.size(); i++) {
            if (chatlist.get(i).getFriendId() == FriendObject.getFriendId()) {
                Toast toast=Toast.makeText(this, "You already have him in chats: "+FriendObject.getYourLocalFriendName(), Toast.LENGTH_SHORT);
                TextView text;
                View vieew = toast.getView();
                text = (TextView) vieew.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.toastTexColor));
                //text.setShadowLayer(0,0,0,0);
                text.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                vieew.setBackgroundResource(R.drawable.toast);
                toast.setView(vieew);
                toast.show();

                return false;
            }
        }
        return true;
    }


}
