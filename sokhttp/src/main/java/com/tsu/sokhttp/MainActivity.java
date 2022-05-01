package com.tsu.sokhttp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "sOKHttp";
    private TextView textView1;
    private Button button1;
    private Button button2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
    }

    private void initViews(){

    }

    private void setListeners(){
        button1.setOnClickListenr(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.main_button1:
                    break;
                case R.id.main_button2:
                    break;
            }
        }
    }
}