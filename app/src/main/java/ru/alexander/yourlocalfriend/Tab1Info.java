package ru.alexander.yourlocalfriend;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Tab1Info extends ParentFragment {
    private static final int LAYOUT=R.layout.tab1_info;
    private static final String TAG_FRAGMENT1 ="UserInfo" ;
    TextInputLayout mYourAge;
    TextInputLayout mYourHobbies;
    TextInputLayout mYourSex;
    Button mTab1SaveBtn;
    private ProgressDialog mSaveProgressBar;
    private DatabaseReference mDataBase;

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
        mSaveProgressBar = new ProgressDialog(getContext());
        mYourAge = (TextInputLayout) rootView.findViewById(R.id.your_age_txt);
        mYourHobbies = (TextInputLayout) rootView.findViewById(R.id.your_hobbies_txt);
        mYourSex = (TextInputLayout) rootView.findViewById(R.id.your_sex_txt);
        mTab1SaveBtn = (Button)rootView.findViewById(R.id.save_your_data);

        mTab1SaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String mYourAgeValue = mYourAge.getEditText().getText().toString();
                String mYourHobbiesValue=mYourHobbies.getEditText().getText().toString();
                String mYourSexValue=mYourSex.getEditText().getText().toString();

                if (TextUtils.isEmpty(mYourAgeValue)||TextUtils.isEmpty(mYourHobbiesValue)||TextUtils.isEmpty(mYourSexValue)) {

                    Toast toast=Toast.makeText(view.getContext(), "Please fill the data above", Toast.LENGTH_SHORT);
                    TextView text;
                    View vieew = toast.getView();
                    text = (TextView) vieew.findViewById(android.R.id.message);
                    text.setTextColor(getResources().getColor(R.color.toastTexColor));
                    //text.setShadowLayer(0,0,0,0);
                    text.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                    vieew.setBackgroundResource(R.drawable.toast);
                    toast.setView(vieew);
                    toast.show();

                }else{

                    save_user_info(mYourAgeValue, mYourHobbiesValue, mYourSexValue);
                }

            }
        });

        return rootView;
    }

    private void save_user_info(String mYourAgeValue, String mYourHobbiesValue, String mYourSexValue) {
        mSaveProgressBar.setTitle("Saving your infor");
        mSaveProgressBar.setMessage("Please wait while saving is completed");
        mSaveProgressBar.setCanceledOnTouchOutside(false);
        mSaveProgressBar.show();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDataBase = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("age", mYourAgeValue);
        userMap.put("hobbies", mYourHobbiesValue);
        userMap.put("sex", mYourSexValue);
        mDataBase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mSaveProgressBar.dismiss();

                }
                else{
                    Toast toast=Toast.makeText(getContext(), "Your info was not saved, please check internet connection ", Toast.LENGTH_SHORT);
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
        });
    }


}
