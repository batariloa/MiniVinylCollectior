package com.batarilo.vinylcollection.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.interactors.record_list.AddToCollection
import com.batarilo.vinylcollection.interactors.record_list.ReadAllFromHistory
import com.batarilo.vinylcollection.interactors.notes.SetRecordNote
import com.batarilo.vinylcollection.interactors.record_list.RemoveRecord
import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection
import com.batarilo.vinylcollection.ui.dialog.NoteDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val readAllFromHistory: ReadAllFromHistory,
    private val addToCollection: AddToCollection,
    private val setRecordNote: SetRecordNote,
    private val removeRecord: RemoveRecord
)
    : ViewModel(){


    fun addToCollection(record: Record){
        viewModelScope.launch(Dispatchers.IO) {
            addToCollection.execute(record)
        }
    }

    lateinit var recordAdapter: RecordAdapterCollection

    fun readAllFromHistory(){
    readAllFromHistory.execute().onEach { dataState->
      dataState.data?.let { list ->
          recordAdapter.records = list
      }
  }.launchIn(viewModelScope)

    }


    fun setRecordNote(context: Context, position:Int): NoteDialog {
        return NoteDialog(context, recordAdapter.records[position].record,setRecordNote)
    }


    fun deleteRecord(position: Int){

        viewModelScope.launch(Dispatchers.IO){
            removeRecord.execute(recordAdapter.records[position])

        }

        val recordCut =ArrayList (recordAdapter.records)
        recordCut.removeAt(position)
        recordAdapter.records = recordCut
    }


}