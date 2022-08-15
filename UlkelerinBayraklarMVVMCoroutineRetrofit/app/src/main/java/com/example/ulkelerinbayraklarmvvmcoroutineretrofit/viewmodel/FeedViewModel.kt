package com.example.ulkelerinbayraklarmvvmcoroutineretrofit.viewmodel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.database.CountryDao
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.database.CountryDatabase
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.model.Country
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.service.CountryAPIService
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.util.CustomSharedPrefences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FeedViewModel(application: Application):BaseViewModel(application) {
    private var service=CountryAPIService()
    private var composit=CompositeDisposable()
    val country=MutableLiveData<List<Country>>()
    val countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    private var customShared =CustomSharedPrefences(getApplication())
    private var refreshTime=10*60*1000*1000*1000L

    fun refreshData(){
        val updateTime=customShared.getTime()
        if(updateTime !=null && updateTime !=0L &&System.nanoTime()-updateTime<refreshTime ){
            getDataFromSQLite()
        }
        else{
            getDataFromApi()
        }
    }
    private fun getDataFromSQLite(){
        countryLoading.value=true
        launch {
            val countries=CountryDatabase(getApplication()).countryDao().getAllData()
            showData(countries)
            Toast.makeText(getApplication(),"Veritabanından veriler geldi",Toast.LENGTH_LONG).show()
        }
    }

     fun getDataFromApi(){
        countryLoading.value= true
        composit.add(
            service.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeSql(t)
                        Toast.makeText(getApplication(),"Internetten veriler geldi",Toast.LENGTH_LONG).show()
                    }
                    override fun onError(e: Throwable) {
                        countryError.value=true
                        countryLoading.value=false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showData(t:List<Country>){
        country.value=t
        countryError.value=false
        countryLoading.value=false
    }
    private fun storeSql(list:List<Country>){
        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            dao.deleteAllData()
            val listlong=dao.insertAll(*list.toTypedArray())
            var i=0
            while (i<list.size){
                list[i].uuid=listlong[i].toInt()
                i++
            }
            showData(list)
        }
        customShared.saveTime(System.nanoTime())
    }
}