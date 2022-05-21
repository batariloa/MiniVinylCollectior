package com.batarilo.vinylcollection.ui.history

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.data.room.RecordRepository
import com.batarilo.vinylcollection.interactors.record_list.ReadAllFromHistory
import com.batarilo.vinylcollection.interactors.record_list.SearchHistoryRecords
import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val readAllFromHistory: ReadAllFromHistory,
    private val searchHistoryRecords: SearchHistoryRecords,
    val recordDao: RecordDao,
    val recordRepository: RecordRepository
)
    : ViewModel(){


    fun addToCollection(record: RecordInList){
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

    lateinit var recordAdapter: RecordAdapterCollection

    fun readAllFromHistory(){
    viewModelScope.launch(Dispatchers.IO){
        recordAdapter.records = readAllFromHistory.execute()

    }
    }

    fun searchHistory(query:String){

        viewModelScope.launch(Dispatchers.IO){
            recordAdapter.records = searchHistoryRecords.execute(query)        }
    }

}