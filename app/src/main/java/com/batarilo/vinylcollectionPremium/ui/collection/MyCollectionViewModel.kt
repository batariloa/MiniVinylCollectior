package com.batarilo.vinylcollectionPremium.ui.collection

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batarilo.vinylcollectionPremium.R
import com.batarilo.vinylcollectionPremium.interactors.notes.SetRecordNote
import com.batarilo.vinylcollectionPremium.interactors.record_list.ReadAllFromCollection
import com.batarilo.vinylcollectionPremium.interactors.record_list.RemoveRecord
import com.batarilo.vinylcollectionPremium.interactors.record_list.SearchCollectionRecords
import com.batarilo.vinylcollectionPremium.ui.collection.recycle.RecordAdapterCollection
import com.batarilo.vinylcollectionPremium.ui.dialog.NoteDialog
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
    private val setRecordNote: SetRecordNote,
    private val removeRecord: RemoveRecord,
)
    : ViewModel(){

    lateinit var recordAdapter: RecordAdapterCollection

    val query : MutableLiveData<String> = MutableLiveData<String>("")

    fun readAllFromCollection(){
            readAllFromCollection.execute().onEach { dataState->
                dataState.data?.let { list ->
                    recordAdapter.records = list
            }
            }.launchIn(viewModelScope)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchCollection(){

        query.value?.let {
            searchCollectionRecords.execute(it).onEach { dataState->
                dataState.data?.let { list ->
                    recordAdapter.records = list
                    recordAdapter.notifyDataSetChanged()
                }
            }.launchIn(viewModelScope)
        }
    }
    fun setRecordNote(context: Context, position:Int): NoteDialog {
       return NoteDialog(context, recordAdapter.records[position].record,setRecordNote)
    }

    internal fun setupRecyclerView(view: View,
                                   activity: FragmentActivity,
                                   onRecordListenerCollection: RecordAdapterCollection.OnRecordListenerCollection
    ) = view.findViewById<RecyclerView>(R.id.rv_record)?.apply {

        if(!::recordAdapter.isInitialized)
        recordAdapter = RecordAdapterCollection(onRecordListenerCollection)

        adapter =recordAdapter
        layoutManager = LinearLayoutManager(activity)

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