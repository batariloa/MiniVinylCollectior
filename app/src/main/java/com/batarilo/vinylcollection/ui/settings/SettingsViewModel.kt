package com.batarilo.vinylcollection.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.di.VinylApp
import com.batarilo.vinylcollection.interactors.cache.DeleteCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject constructor(
        private val deleteCache: DeleteCache,
        private val context:VinylApp
    )
    : ViewModel() {

    fun deleteCache(){
        viewModelScope.launch(Dispatchers.IO){
            deleteCache.execute(context)
        }

    }

}