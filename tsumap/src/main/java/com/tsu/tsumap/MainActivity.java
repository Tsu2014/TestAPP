package com.tsu.tsumap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView textView1;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.d(TAG , "location : "+aMapLocation.getAddress());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLocation();

        initViews();
        setListeners();
    }
    
    private void initLocation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化定位
                mLocationClient = new AMapLocationClient(getApplicationContext());
                //设置定位回调监听
                mLocationClient.setLocationListener(mLocationListener);
                //初始化AMapLocationClientOption对象
                mLocationOption = new AMapLocationClientOption();

                mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
                if(null != mLocationClient){
                    mLocationClient.setLocationOption(mLocationOption);
                    //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                    mLocationClient.stopLocation();
                    mLocationClient.startLocation();
                }
            }
        }).start();
    }

    private void initViews(){
        textView1 = findViewById(R.id.main_textview1);
        button1 = findViewById(R.id.main_button1);
        button2 = findViewById(R.id.main_button2);
        button3 = findViewById(R.id.main_button3);
        button4 = findViewById(R.id.main_button4);
    }

    private void setListeners(){
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_button1:
                    break;
                case R.id.main_button2:
                    break;
                case R.id.main_button3:
                    break;
                case R.id.main_button4:
                    break;
            }
        }
    };
}