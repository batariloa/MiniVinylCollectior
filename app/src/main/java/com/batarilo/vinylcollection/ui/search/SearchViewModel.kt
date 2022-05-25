package com.batarilo.vinylcollection.ui.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.interactors.record_list.AddToCollection
import com.batarilo.vinylcollection.interactors.record_list.AddToWishList
import com.batarilo.vinylcollection.interactors.record_list.SearchRecordsApi
import com.batarilo.vinylcollection.ui.search.recycle.RecordAdapterSearch
import com.batarilo.vinylcollection.util.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRecordsApi: SearchRecordsApi,
    private val addToWishList: AddToWishList,
    private val addToCollection: AddToCollection,
    private val connectivityManager: ConnectivityManager
)
    : ViewModel() {


    private val loading = mutableStateOf(false)

    lateinit var recordAdapterSearch: RecordAdapterSearch


    fun addRecordToCollection(record:Record){
        viewModelScope.launch(Dispatchers.IO) {

            addToCollection.execute(record)
        }
    }

    fun addRecordToWishlist(record: Record){
        println("ADD TO WISHLIST $record")
;        viewModelScope.launch(Dispatchers.IO) {
            addToWishList.execute(record)
        }
    }

     fun newSearch(query:String){

        searchRecordsApi.execute(query, connectivityManager.isNetworkAvailable.value).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
                recordAdapterSearch.records = list.map { list -> list.record }
                recordAdapterSearch.notifyDataSetChanged()
            }
            dataState.error?.let { error ->
                Log.d("TAG", "Here is the error: $error")
            }
        }.launchIn(viewModelScope)
    }





}