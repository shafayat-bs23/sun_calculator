package com.example.bs217.suncalculation

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val update = thread(this)
        update.start()

        // timeTextView is a text view which shows
        // current time. It is accessible from here.

    }

    class thread():Thread(){

        lateinit var activity:Activity

        constructor(activity: Activity) : this() {
            this.activity = activity
        }

        override fun run() {

            while(true){
                updateTime(activity.timeTextView)
            }


        }

        private fun updateTime(timeTextView: TextView?) {
            val current = System.currentTimeMillis()
            val calender = Calendar.getInstance()
            calender.timeInMillis = current
            val h = calender.get(Calendar.HOUR)
            val m = calender.get(Calendar.MINUTE)
            val s = calender.get(Calendar.SECOND)
            timeTextView!!.text = h.toString()+":"+m.toInt().toString()+":"+s.toInt().toString()
        }

    }

}
