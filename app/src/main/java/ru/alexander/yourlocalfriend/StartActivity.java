package ru.alexander.yourlocalfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by ekaterina on 23/01/2018.
 */

public class StartActivity extends AppCompatActivity {

    private Button mRegBtn;
    private Button mLogBtn;

    @Override
    protected void onCreate (Bundle savedBundleInstance){
        super.onCreate(savedBundleInstance);

        setContentView(R.layout.start_activity);
        mRegBtn=(Button) findViewById(R.id.start_reg_btn);
        mLogBtn=(Button) findViewById(R.id.start_login_btn);

        mRegBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent reg_intent=new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(reg_intent);
            }
        });

        mLogBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent loging_intent=new Intent(StartActivity.this, LoginActivity.class);
                startActivity(loging_intent);
            }
        });
    }

}
