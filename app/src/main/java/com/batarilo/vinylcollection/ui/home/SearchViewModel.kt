package com.batarilo.vinylcollection.ui.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.retrofit.RecordApiService
import com.batarilo.vinylcollection.data.retrofit.RetrofitInstance
import com.batarilo.vinylcollection.data.room.RecordRepository
import com.batarilo.vinylcollection.interactors.record_list.SearchRecords
import com.batarilo.vinylcollection.ui.home.recycle.RecordAdapterSearch
import com.batarilo.vinylcollection.util.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val searchRecords: SearchRecords,
    private val connectivityManager: ConnectivityManager
)
    : ViewModel() {



    val record:MutableState<Record?> = mutableStateOf(null)
    val query = mutableStateOf("")
    val loading = mutableStateOf(false)
    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    val tag = "Viewmodel tag"
    lateinit var recordAdapterSearch: RecordAdapterSearch

    val records : MutableState<List<Record>> = mutableStateOf(ArrayList())


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

        Log.d("TAG","query: $query")
        searchRecords.execute(query.value, connectivityManager.isNetworkAvailable.value).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
            records.value = list
                recordAdapterSearch.records= list
                println("LISTA REZULTATA $list")
            }
            dataState.error?.let { error ->
                Log.d("TAG", "Heres the error: $error")
            }
        }.launchIn(viewModelScope)
    }











}