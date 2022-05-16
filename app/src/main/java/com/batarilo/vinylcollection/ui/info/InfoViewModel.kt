package com.batarilo.vinylcollection.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordRepository
import com.batarilo.vinylcollection.ui.home.recycle.RecordAdapterSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val recordRepository: RecordRepository
)
    : ViewModel() {

    suspend fun addRecordToHistory(record: Record){
        recordRepository.addRecordToHistory(record)

    }



}