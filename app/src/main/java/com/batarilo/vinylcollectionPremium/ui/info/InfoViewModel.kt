package com.batarilo.vinylcollectionPremium.ui.info

import android.widget.ImageButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollectionPremium.R
import com.batarilo.vinylcollectionPremium.data.model.Record
import com.batarilo.vinylcollectionPremium.interactors.record_list.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val addToHistory: AddToHistory,
    private val recordExistsInCollection: RecordExistsInCollection,
    private val recordExistsInWishlist: RecordExistsInWishlist,
    private val addToWishList: AddToWishList,
    private val addToCollection: AddToCollection,
    private val removeFromCollection: RemoveFromCollection,
    private val removeFromWishlist: RemoveFromWishlist
)
    : ViewModel() {

        fun addToHistory(record: Record){

            viewModelScope.launch(Dispatchers.IO){
                addToHistory.execute(record)

            }
        }



     fun recordInCollectionExists(record: Record?, buttonCollection:ImageButton){


         if (record != null) {
             recordExistsInCollection.execute(record.id).onEach { dataState->
                 dataState.data?.let { result ->

                     buttonCollection.apply {
                         if (result)
                             setImageResource(R.drawable.ic_baseline_playlist_add_check_24)
                         else {
                             setImageResource(R.drawable.ic_baseline_playlist_add_24)
                         } } } }.launchIn(scope = CoroutineScope(Dispatchers.IO))
         }

    }
    fun recordInWishlistExists(record: Record?, buttonWishlist: ImageButton){

        if (record != null) {
            recordExistsInWishlist.execute(record.id).onEach { dataState->
                dataState.data?.let { result ->

                    buttonWishlist.apply {
                        if (result)
                            setImageResource(R.drawable.ic_star_filled)
                        else {
                            setImageResource(R.drawable.ic_baseline_star_border_35)
                        } } } }.launchIn(scope = CoroutineScope(Dispatchers.IO))
        }
    }

    fun addRecordToCollection(record:Record?, btn:ImageButton){

        if (record != null) {
            recordExistsInCollection.execute(record.id).onEach { dataState ->

                dataState.data?.let { result ->
                    if (result){
                        btn.setImageResource(R.drawable.ic_baseline_playlist_add_24)
                        removeFromCollection.execute(record.id)
                    }
                    else{
                        addToCollection.execute(record)
                        btn.setImageResource(R.drawable.ic_baseline_playlist_add_check_24)
                    }
                }

            }.launchIn(viewModelScope)
                .invokeOnCompletion { }
        }
    }

    fun addRecordToWishlist(record: Record?, btn: ImageButton){


        if (record != null) {
            recordExistsInWishlist.execute(record.id).onEach { dataState ->

                dataState.data?.let { result ->
                    if (result){
                        btn.setImageResource(R.drawable.ic_baseline_star_border_35)
                        removeFromWishlist.execute(record.id)
                    }
                    else{
                        addToWishList.execute(record)
                        btn.setImageResource(R.drawable.ic_star_filled)
                }}

            }.launchIn(viewModelScope)
                .invokeOnCompletion {  }
        }
    }





}