package com.batarilo.vinylcollection.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.di.VinylApp
import com.batarilo.vinylcollection.interactors.record_list.*
import com.batarilo.vinylcollection.ui.search.recycle.RecordAdapterSearch
import com.batarilo.vinylcollection.util.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
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
    val recordExistsInCollection: RecordExistsInCollection,
    val recordExistsInWishlist: RecordExistsInWishlist,
    private val removeFromWishlist: RemoveFromWishlist,
    private val removeFromCollection: RemoveFromCollection,
    val context:VinylApp
)
    : ViewModel() {

    val query : MutableLiveData<String> = MutableLiveData<String>("")
    private val loading = mutableStateOf(false)

    lateinit var recordAdapterSearch: RecordAdapterSearch


    fun addRecordToCollection(position:Int){
        val item = recordAdapterSearch.records[position]

        recordExistsInCollection.execute(item.record.id).onEach { dataState ->

            dataState.data?.let { result ->
                if (result){
                    println("VEC POSTOJI")
                    removeFromCollection.execute(item.record.id)
                }
                else
                    addToCollection.execute(item.record)
            }

        }.launchIn(viewModelScope)
            .invokeOnCompletion { recordAdapterSearch.notifyItemChanged(position) }
    }

    fun addRecordToWishlist(position: Int){
        val item = recordAdapterSearch.records[position]

        recordExistsInWishlist.execute(item.record.id).onEach { dataState ->

               dataState.data?.let { result ->
                   if (result){
                       println("VEC POSTOJI")
                       removeFromWishlist.execute(item.record.id)
                   }
                   else
                       addToWishList.execute(item.record)
               }

        }.launchIn(viewModelScope)
            .invokeOnCompletion { recordAdapterSearch.notifyItemChanged(position) }
    }

    fun setupRecyclerView(
        view: View,
        onRecordListenerSearch: RecordAdapterSearch.OnRecordListenerSearch,
        activity: FragmentActivity
    ) = view.findViewById<RecyclerView>(R.id.rv_record)?.apply {
        (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        if(!::recordAdapterSearch.isInitialized) // if not initialized
        recordAdapterSearch = RecordAdapterSearch(
            onRecordListenerSearch,
            recordExistsInCollection,
           recordExistsInWishlist)

        adapter = recordAdapterSearch
        layoutManager = LinearLayoutManager(activity)

    }

     @SuppressLint("NotifyDataSetChanged")
     fun newSearch(){

        val cacheOn = PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean("cache",false)



         query.value?.let {
             searchRecordsApi.execute(it, connectivityManager.isNetworkAvailable.value, cacheOn)
                 .onEach { dataState ->
                 loading.value = dataState.loading

                 dataState.data?.let { result ->
                     println("RESULTS ARE $result")
                     recordAdapterSearch.records = result
                     recordAdapterSearch.notifyDataSetChanged()
                 }
                 dataState.error?.let { error ->
                     Log.d("TAG", "Here is the error: $error")
                 }
             }.launchIn(viewModelScope)
         }
    }






}