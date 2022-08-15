package com.example.ulkelerinbayraklarmvvmcoroutineretrofit.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPrefences {
    companion object{
        private val PREFENCES_TIME="prefences_time"
        private var shared:SharedPreferences?=null
        @Volatile
        private var instance:CustomSharedPrefences?=null
        private val lock=Any()

        operator fun invoke(context: Context) : CustomSharedPrefences= instance?: synchronized(lock){
            instance ?: customsharedPrefences(context).also {
                instance=it
            }
        }
        private fun customsharedPrefences(context: Context):CustomSharedPrefences{
            shared=PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPrefences()
        }
    }

    fun saveTime(time:Long){
        shared?.edit(commit = true){
            putLong(PREFENCES_TIME,time)
        }
    }

    fun getTime()= shared?.getLong(PREFENCES_TIME,0)
}