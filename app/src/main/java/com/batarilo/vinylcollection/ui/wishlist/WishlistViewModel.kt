package com.batarilo.vinylcollection.ui.wishlist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordRepository
import com.batarilo.vinylcollection.interactors.record_list.ReadAllFromWishlist
import com.batarilo.vinylcollection.interactors.record_list.SearchWishlist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    val recordRepository: RecordRepository,
    val readAllFromWishlist: ReadAllFromWishlist,
    val searchWishlist: SearchWishlist
)
    : ViewModel() {

    private val loading = mutableStateOf(false)

    lateinit var recordAdapter:RecordAdapterWishlist


    fun removeRecord(record: RecordInList){
            viewModelScope.launch(Dispatchers.IO) {
                recordRepository.deleteRecordInList(record)
            }
        }


    fun readAllFromWishlist(){
        viewModelScope.launch(Dispatchers.IO) {
            recordAdapter.records = readAllFromWishlist.execute()
        }

    }

    fun searchWishlist(query:String){

        viewModelScope.launch(Dispatchers.IO){
         recordAdapter.records = searchWishlist.execute(query)
        }
    }


}