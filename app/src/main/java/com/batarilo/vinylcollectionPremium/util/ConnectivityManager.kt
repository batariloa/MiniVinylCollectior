package com.batarilo.vinylcollectionPremium.util

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import javax.inject.Singleton


//class that provides connection status in other parts of the app
@Singleton
class ConnectivityManager
@Inject
constructor(
    application: Application,
) {
    internal val connectionLiveData = ConnectionLiveData(application)

    // observe this in ui

    val isNetworkAvailable = mutableStateOf(false)

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner){
        connectionLiveData.observe(lifecycleOwner) { isConnected ->
            isConnected?.let { isNetworkAvailable.value = it }
        }
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner){
        connectionLiveData.removeObservers(lifecycleOwner)
    }
}