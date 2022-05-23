package com.batarilo.vinylcollection.ui.wishlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.interactors.record_list.ReadAllFromWishlist
import com.batarilo.vinylcollection.interactors.record_list.SearchWishlist
import com.batarilo.vinylcollection.interactors.notes.SetRecordNote
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
    private val setRecordNote: SetRecordNote
)
    : ViewModel() {


    lateinit var recordAdapter:RecordAdapterWishlist


    fun removeRecord(record: RecordInList){
            viewModelScope.launch(Dispatchers.IO) {
            }
        }


    fun readAllFromWishlist(){
        readAllFromWishlist.execute().onEach { dataState->
            dataState.data?.let { list ->
                recordAdapter.records = list
            }
        }.launchIn(viewModelScope)

    }


    fun searchWishlist(query:String){

        viewModelScope.launch(Dispatchers.IO){
         recordAdapter.records = searchWishlist.execute(query)
        }
    }
    fun setRecordNote(context: Context, position:Int): NoteDialog {
        return NoteDialog(context, recordAdapter.records[position],setRecordNote)
    }


}