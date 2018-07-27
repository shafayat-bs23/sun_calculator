package com.example.bs217.suncalculation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.PermissionChecker.checkSelfPermission
import java.util.*
import kotlin.collections.ArrayList

class LocationTracker(var mActivity : MainActivity){

    var longitude : Double = 0.0
    var latitude : Double = 0.0
    private lateinit var permissionsToRequest: Array<String>
    private var permissions : Array<String> = Array(2){""}
    private val ALL_PERMISSIONS_RESULT = 101
    lateinit var locationTrack : LocationTrack

    fun getLocation() {
        permissions.set(0, ACCESS_FINE_LOCATION)
        permissions.set(1, ACCESS_COARSE_LOCATION)

        permissionsToRequest = findUnAskedPermissions(permissions)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(permissionsToRequest.size > 0)

                requestPermissions(mActivity, permissionsToRequest, ALL_PERMISSIONS_RESULT)
        }
        locationTrack = LocationTrack(mActivity.applicationContext)
        if(locationTrack.canGetLocation){
            longitude = locationTrack.getLongitude()
            latitude = locationTrack.getLatitude()
            mActivity.updateLatLon(latitude, longitude)
        }else{
            locationTrack.showSettingsAlert()
        }
    }

    private fun findUnAskedPermissions(permissions: Array<out String>): Array<String> {
        var results = Array<String>(permissions.size){""}
        var index = 0

        for(perm : String in permissions){
            if(!hasPermission(perm)){
                results.set(index, perm)
                index++
            }
        }
        return results
    }

    private fun hasPermission(permission: Any): Boolean {
        if(canMakeSmores()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                return (checkSelfPermission(mActivity, permission.toString()) == PackageManager.PERMISSION_GRANTED)
            }
        }
        return true
    }

    private fun canMakeSmores(): Boolean {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
    }
}