package com.batarilo.vinylcollection.ui.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.interactors.record_list.*
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
    private val connectivityManager: ConnectivityManager,
    val existsInCollection: RecordExistsInCollection,
    val existsInWishlist: RecordExistsInWishlist
)
    : ViewModel() {


    private val loading = mutableStateOf(false)

    lateinit var recordAdapterSearch: RecordAdapterSearch


    fun addRecordToCollection(position:Int){
        viewModelScope.launch(Dispatchers.IO) {

            addToCollection.execute(recordAdapterSearch.records[position])
        }
        recordAdapterSearch.notifyItemChanged(position)
    }

    fun addRecordToWishlist(position: Int){
        viewModelScope.launch(Dispatchers.IO) {
            addToWishList.execute(recordAdapterSearch.records[position]) }
        recordAdapterSearch.notifyItemChanged(position)
    }

     @SuppressLint("NotifyDataSetChanged")
     fun newSearch(query:String){

        searchRecordsApi.execute(query, connectivityManager.isNetworkAvailable.value).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { result ->
                recordAdapterSearch.records = result.map { list -> list.record }
                recordAdapterSearch.notifyDataSetChanged()
            }
            dataState.error?.let { error ->
                Log.d("TAG", "Here is the error: $error")
            }
        }.launchIn(viewModelScope)
    }






}