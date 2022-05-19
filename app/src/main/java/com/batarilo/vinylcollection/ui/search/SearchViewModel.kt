package com.batarilo.vinylcollection.ui.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.room.RecordRepository
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
    private val recordRepository: RecordRepository,
    private val searchRecordsApi: SearchRecordsApi,
    private val connectivityManager: ConnectivityManager
)
    : ViewModel() {


    val query = mutableStateOf("")
    private val loading = mutableStateOf(false)

    lateinit var recordAdapterSearch: RecordAdapterSearch

    private val records : MutableState<List<Record>> = mutableStateOf(ArrayList())


    fun addRecordToCollection(record:Record){
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.addRecordToCollection(record)
        }
    }

    fun addRecordToWishlist(record: Record){
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.addRecordToWishlist(record)
        }
    }

     fun newSearch(){

        searchRecordsApi.execute(query.value, connectivityManager.isNetworkAvailable.value).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
            records.value = list
                recordAdapterSearch.records= list
                println("LISTA REZULTATA $list")
            }
            dataState.error?.let { error ->
                Log.d("TAG", "Here is the error: $error")
            }
        }.launchIn(viewModelScope)
    }











}