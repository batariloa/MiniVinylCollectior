package com.batarilo.vinylcollection.ui.history

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
class HistoryViewModel @Inject constructor(
    val recordRepository: RecordRepository
)
    : ViewModel(){

    fun readAllFromHistory(): LiveData<List<RecordInList>> {
    return recordRepository.readFromHistory()
    }
    fun addToCollection(record: RecordInList){
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.deleteRecordInList(record)
        }
    }

}