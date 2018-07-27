package com.example.bs217.suncalculation

import android.os.Handler
import android.view.View
import java.text.DecimalFormat
import java.util.*

class MainPresenter : MainPresenterInterface {

    lateinit var activity: MainActivity

    val handler = Handler()
    val runnable = object : Runnable {
        override fun run() {
            activity.updateTimeAndDate(calculateTime(), calculateDate())
            handler.postDelayed(this, 1000)
        }

    }

    private fun updateUi() {

    }

    private fun getCalender(): Calendar {
        val calender = Calendar.getInstance()
        val current = System.currentTimeMillis()
        calender.timeInMillis = current
        return calender
    }

    override fun calculateTime() : String {

        val calender = getCalender()
        val formatter = DecimalFormat("00")
        val hour = formatter.format(calender.get(Calendar.HOUR))
        val minute = formatter.format(calender.get(Calendar.MINUTE))
        val second = formatter.format(calender.get(Calendar.SECOND))
        return hour+":"+minute+":"+second

    }


    override fun calculateDate() : String {
        val calender = getCalender()
        val year = calender.get(Calendar.YEAR).toString()
        val month = calender.get(Calendar.MONTH).toString()
        val day = calender.get(Calendar.DAY_OF_MONTH).toString()

        return day+"-"+month+"-"+year
    }

    fun bindView(mainActivity: MainActivity) {
        activity = mainActivity

    }

    fun startClock(){
        handler.post(runnable)
    }

    fun stopClock() {
        handler.removeCallbacks(runnable)
    }


}