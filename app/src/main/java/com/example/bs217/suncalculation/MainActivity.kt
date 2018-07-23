package com.example.bs217.suncalculation

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    val runnable = object : Runnable {
        override fun run() {
            updateTime()
            handler.postDelayed(this, 1000)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        handler.post(runnable)
//        val update = thread(this)
//        update.run()
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }


//    inner class thread: Thread(){
//
//        override fun run() {
//            updateTime()
//            handler.postDelayed(this, 1000)
//
//        }
//
//    }

    private fun updateTime() {
        Log.d("Running ", "here")
        val current = System.currentTimeMillis()
        val calender = Calendar.getInstance()
        calender.timeInMillis = current
        val h = calender.get(Calendar.HOUR)
        val m = calender.get(Calendar.MINUTE)
        val s = calender.get(Calendar.SECOND)
        timeTextView?.text = h.toString()+":"+m.toInt().toString()+":"+s.toInt().toString()
    }

}
