package com.example.ulkelerinbayraklarmvvmcoroutineretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.database.CountryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract  class BaseViewModel(application: Application) : AndroidViewModel(application),CoroutineScope {
    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}