package com.example.bs217.suncalculation

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import android.support.v4.content.ContextCompat.startActivity
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.content.Intent
import android.os.Bundle
import android.provider.Settings


class LocationTrack : LocationListener {

    private var context : Context
    private lateinit var location : Location

    constructor(context: Context){
        this.context = context
        getLocation()

    }

    var checkGPS = false
    var canGetLocation = false

    var lat : Double = 0.0
    fun getLatitude() : Double {
        return lat
    }

    var lon : Double = 0.0
    fun getLongitude() : Double{

        return lon
    }

    private val MIN_DISTANCE_CHANGE_FOR_UPDATES = 10.0f
    private val MIN_TIME_BW_UPDATES : Long = 1000 * 60 * 1
    lateinit var locationManager : LocationManager

    private fun getLocation(){
        try{
            locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
            checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if(!checkGPS){
                Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show()
            }else{

                if(checkGPS){

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED){}

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            this)

                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
                    lat = location.latitude
                    lon = location.longitude
                    this.canGetLocation = true


                }
            }
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun showSettingsAlert(){
        var alertDialog = AlertDialog.Builder(context)

        alertDialog.setTitle("GPS is not Enabled!")
        alertDialog.setMessage("Do you want to turn on GPS?")

        alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        })

        alertDialog.setNegativeButton("No", DialogInterface.OnClickListener{dialog: DialogInterface?, which: Int ->
            dialog?.cancel()
        })
    }

    override fun onLocationChanged(location: Location) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
