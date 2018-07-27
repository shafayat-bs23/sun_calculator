package com.example.bs217.suncalculation

import android.app.Activity
import android.content.Context
import android.view.View

abstract class MainActivityPresenter{

    lateinit private var activity : Activity

    constructor()

    constructor(activity: MainActivity) {
        this.activity = activity
    }

    fun getLocation(){

    }
    abstract fun setLocation()

    fun getContext() : Context{
        return activity.applicationContext
    }

    fun getActivity() : Activity{
        return activity
    }

}