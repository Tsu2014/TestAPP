package com.tsu.kapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val  TAG:String = "KAPP"
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var textView:TextView

    private lateinit var timeChangeReceiver: TimeChangeReceiver

    private val onClickListener:View.OnClickListener = View.OnClickListener {
        when (it.id){
            R.id.main_button2 ->{
                Toast.makeText(this , "Button2" , Toast.LENGTH_SHORT).show()
                Log.d(TAG , "Button2")
            }
            R.id.main_button1 ->{
                Toast.makeText(this , "Button1" , Toast.LENGTH_SHORT).show()
                Log.d(TAG , "Button1")
            }
        }
    }

    inner class TimeChangeReceiver :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            Toast.makeText(p0 , "Time has changed" , Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")

        timeChangeReceiver = TimeChangeReceiver()
        registerReceiver(timeChangeReceiver , intentFilter)

        initViews()
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }

    fun initViews(){
        button1 = findViewById(R.id.main_button1)
        button2 = findViewById(R.id.main_button2)
        textView = findViewById(R.id.main_textview01)
    }

    fun setListeners(){
        /*button1.setOnClickListener(){
            Toast.makeText(this , "button1" , Toast.LENGTH_SHORT).show()
            Log.d(TAG , "button1")
        }

        button2.setOnClickListener(){
            Toast.makeText(this , "button2" , Toast.LENGTH_SHORT).show()
            Log.d(TAG , "button2")
        }*/
        button1.setOnClickListener(onClickListener)
        button2.setOnClickListener(onClickListener)
    }
}