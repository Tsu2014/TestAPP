package com.tsu.mycanlendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsu.mycanlendar.canlendar.CalendarManager;
import com.tsu.mycanlendar.canlendar.ExchangeTest;
import com.tsu.mycanlendar.canlendar.ImapTest;
import com.tsu.mycanlendar.canlendar.SmtpTest;

import microsoft.exchange.webservices.data.ExchangeService;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private TextView textView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private CalendarManager calendarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListeners();
        calendarManager = CalendarManager.getInstance();
    }

    private void initView(){
        textView = findViewById(R.id.main_textview01);
        button1 = findViewById(R.id.main_button01);
        button2 = findViewById(R.id.main_button02);
        button3 = findViewById(R.id.main_button03);
        button4 = findViewById(R.id.main_button04);
    }

    private void setListeners(){
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.main_button01:
                    action1();
                    break;
                case R.id.main_button02:
                    action2();
                    break;
                case R.id.main_button03:
                    action3();
                    break;
                case R.id.main_button04:
                    action4();
                    break;
            }
        }
    };

    private void action1(){
        Log.d(TAG , "action1");
        int result = calendarManager.checkCalendarAccount(this.getApplicationContext());
        textView.setText("result : "+result);
    }

    private void action2(){
        Log.d(TAG , "action2");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //ExchangeTest.getInstance().getCanlendar();
                    ExchangeTest.getInstance().sendMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        textView.setText("action2");
    }

    private void action3(){
        Log.d(TAG , "action3");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //SmtpTest.getInstance().sendBy126();
                    SmtpTest.getInstance().sendByQQ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        textView.setText("action3");
    }

    private void action4(){
        Log.d(TAG , "action4");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ImapTest.getInstance().getInputBox();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        textView.setText("action4");
    }


}