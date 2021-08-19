package com.example.bs217.suncalculation

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), MainView {


    lateinit var presenter : MainPresenter
    lateinit var locationTracker : LocationTracker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.addLogAdapter(AndroidLogAdapter())

        Logger.d(BuildConfig.KEY)

        presenter = MainPresenter()
        presenter.bindView(this)
        locationTracker = LocationTracker(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.startClock()
        locationTracker.getLocation()
//        val update = thread(this)
//        update.run()
    }

    override fun onStop() {
        super.onStop()
        presenter.stopClock()
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

    override fun updateTimeAndDate(time : String, date : String) {
        timeTextView.text = time
        dateTextView.text = date
    }

    override fun updateLatLon(lat: Double, lon: Double) {
        latitudeTextView.text = lat.toString()
        longitudeTextView.text = lon.toString()

    }
}
