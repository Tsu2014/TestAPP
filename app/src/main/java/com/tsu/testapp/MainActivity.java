package com.tsu.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "APP";
    private TextView textView1;
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();
    }

    private void initViews(){
        textView1 = findViewById(R.id.main_textview1);
        button1 = findViewById(R.id.main_button1);
        button2 = findViewById(R.id.main_button2);
    }

    private void setListeners(){
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.main_button1:
                    action1();
                    break;
                case R.id.main_button2:
                    action2();
                    break;
            }
        }
    };

    private void action1(){
        Log.d(TAG , "action1");
        try {
            getChannelInfo();
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

    }

    private void getChannelInfo() throws PackageManager.NameNotFoundException{
        PackageManager pm = getPackageManager();
        ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName() , PackageManager.GET_META_DATA);
        String channel = appInfo.metaData.getString("CHANNEL_VALUE");
        textView1.setText(channel);
    }

    private void action2(){
        Log.d(TAG , "action2");
    }
}