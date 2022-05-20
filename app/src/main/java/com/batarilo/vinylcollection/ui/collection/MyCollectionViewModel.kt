package com.batarilo.vinylcollection.ui.collection

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordRepository
import com.batarilo.vinylcollection.interactors.record_list.ReadAllFromCollection
import com.batarilo.vinylcollection.interactors.record_list.SearchCollectionRecords
import com.batarilo.vinylcollection.interactors.record_list.SearchRecordsApi
import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCollectionViewModel @Inject constructor(
    val recordRepository: RecordRepository,
    private val searchCollectionRecords: SearchCollectionRecords,
    private val readAllFromCollection: ReadAllFromCollection
)
    : ViewModel(){

    lateinit var recordAdapter: RecordAdapterCollection



    fun readAllFromCollection(){
        viewModelScope.launch(Dispatchers.IO){
          val records = readAllFromCollection.execute()
            recordAdapter.records = records
        }
    }

    fun searchCollection(query:String){

        viewModelScope.launch(Dispatchers.IO) {
          val records = searchCollectionRecords.execute(query)
            recordAdapter.records = records
        }

    }
}