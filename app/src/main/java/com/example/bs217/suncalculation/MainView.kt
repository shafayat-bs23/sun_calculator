package com.example.bs217.suncalculation

interface MainView {

    fun updateTimeAndDate(time : String, date : String)
    fun updateLatLon(lat : Double, lon : Double)
}