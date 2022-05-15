package com.batarilo.vinylcollection.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.room.RecordRepository
import com.batarilo.vinylcollection.ui.home.recycle.RecordAdapterSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recordRepository: RecordRepository
)
    : ViewModel() {

    var izadji = 0
    lateinit var recordAdapterSearch: RecordAdapterSearch

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