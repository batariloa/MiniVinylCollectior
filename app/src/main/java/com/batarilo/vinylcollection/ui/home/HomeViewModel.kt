package com.batarilo.vinylcollection.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.room.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recordRepository: RecordRepository
)
    : ViewModel() {

    fun addRecordToCollection(record:Record){
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.addRecordToCollection(record)
        }
    }

    fun addRecordToWishlist(record: Record){
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.addRecordToWishlist(record)
        }
    }


}