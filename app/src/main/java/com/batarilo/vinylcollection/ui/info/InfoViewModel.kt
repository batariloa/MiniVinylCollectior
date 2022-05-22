package com.batarilo.vinylcollection.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.interactors.record_list.AddToHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
private val addToHistory: AddToHistory
)
    : ViewModel() {

        fun addToHistory(record: Record){

            viewModelScope.launch(Dispatchers.IO){
                addToHistory.execute(record)

            }
        }




}