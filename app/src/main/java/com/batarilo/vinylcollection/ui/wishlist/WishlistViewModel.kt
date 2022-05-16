package com.batarilo.vinylcollection.ui.wishlist

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
class WishlistViewModel @Inject constructor(
    val recordRepository: RecordRepository
)
    : ViewModel() {

        fun removeRecord(record: RecordInList){
            viewModelScope.launch(Dispatchers.IO) {
                recordRepository.deleteRecordInList(record)
            }
        }

        fun loadWishList(): LiveData<List<RecordInList>> {

              return  recordRepository.readFromWishlist()
        }



}