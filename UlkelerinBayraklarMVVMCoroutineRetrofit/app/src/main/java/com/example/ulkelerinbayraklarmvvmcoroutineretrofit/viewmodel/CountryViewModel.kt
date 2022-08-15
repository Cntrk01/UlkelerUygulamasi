package com.example.ulkelerinbayraklarmvvmcoroutineretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.database.CountryDatabase
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.model.Country
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) :BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataRoom(id:Int){
        launch {
           val dao=CountryDatabase(getApplication()).countryDao().getCountry(id)
            countryLiveData.value=dao
        }
    }
}