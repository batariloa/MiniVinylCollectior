package com.batarilo.vinylcollection.ui.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCollectionViewModel @Inject constructor(
    val recordRepository: RecordRepository
)
    : ViewModel(){

    fun readAllFromCollection(): LiveData<List<RecordInList>> {
    return recordRepository.readFromCollection()
    }
    fun removeRecordFromCollection(record: RecordInList){
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.deleteRecordInList(record)
        }
    }

}