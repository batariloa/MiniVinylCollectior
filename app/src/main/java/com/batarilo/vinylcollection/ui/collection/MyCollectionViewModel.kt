package com.batarilo.vinylcollection.ui.collection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.interactors.record_list.ReadAllFromCollection
import com.batarilo.vinylcollection.interactors.record_list.SearchCollectionRecords
import com.batarilo.vinylcollection.interactors.notes.SetRecordNote
import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection
import com.batarilo.vinylcollection.ui.dialog.NoteDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCollectionViewModel @Inject constructor(
    private val searchCollectionRecords: SearchCollectionRecords,
    private val readAllFromCollection: ReadAllFromCollection,
    private val setRecordNote: SetRecordNote
)
    : ViewModel(){

    lateinit var recordAdapter: RecordAdapterCollection



    fun readAllFromCollection(){
            readAllFromCollection.execute().onEach { dataState->
                dataState.data?.let { list ->
                    recordAdapter.records = list
            }
            }.launchIn(viewModelScope)
    }

    fun searchCollection(query:String){

        viewModelScope.launch(Dispatchers.IO) {
          val records = searchCollectionRecords.execute(query)
            recordAdapter.records = records
        }

    }
    fun setRecordNote(context: Context, position:Int): NoteDialog {
       return NoteDialog(context, recordAdapter.records[position],setRecordNote)
    }


}