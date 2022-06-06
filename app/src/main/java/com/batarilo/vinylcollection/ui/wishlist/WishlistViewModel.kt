package com.batarilo.vinylcollection.ui.wishlist

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.interactors.notes.SetRecordNote
import com.batarilo.vinylcollection.interactors.record_list.ReadAllFromWishlist
import com.batarilo.vinylcollection.interactors.record_list.RemoveRecord
import com.batarilo.vinylcollection.interactors.record_list.SearchWishlist
import com.batarilo.vinylcollection.ui.dialog.NoteDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val readAllFromWishlist: ReadAllFromWishlist,
    private val searchWishlist: SearchWishlist,
    private val setRecordNote: SetRecordNote,
    private val removeRecord: RemoveRecord
)
    : ViewModel() {

    val query : MutableLiveData<String> = MutableLiveData<String>("")
    lateinit var recordAdapter:RecordAdapterWishlist
    fun readAllFromWishlist(){
        readAllFromWishlist.execute().onEach { dataState->
            dataState.data?.let { list -> recordAdapter.records = list }
        }.launchIn(viewModelScope)
    }


    fun searchWishlist(){

        query.value?.let {
            searchWishlist.execute(it).onEach { dataState->
                dataState.data?.let { list -> recordAdapter.records = list }
                recordAdapter.notifyDataSetChanged()
            }.launchIn(viewModelScope)
        }
    }

    fun setRecordNote(context: Context, position:Int): NoteDialog {
        return NoteDialog(context, recordAdapter.records[position].record,setRecordNote)
    }

    fun deleteRecord(position: Int){
        viewModelScope.launch(Dispatchers.IO){
            removeRecord.execute(recordAdapter.records[position])
        }

        val recordCut = ArrayList (recordAdapter.records)
        recordCut.removeAt(position)
        recordAdapter.records = recordCut
    }

    internal fun setupRecyclerView(view: View,
                                   activity:FragmentActivity,
                                   onRecordListenerWishlist: RecordAdapterWishlist.OnRecordListenerWishlist
    ) = view.findViewById<RecyclerView>(R.id.rv_record)?.apply {
        if(!::recordAdapter.isInitialized)
        recordAdapter = RecordAdapterWishlist(onRecordListenerWishlist)

        adapter =recordAdapter
        layoutManager = LinearLayoutManager(activity)

    }



}