package com.batarilo.vinylcollection.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.retrofit.RecordApiService
import com.batarilo.vinylcollection.data.retrofit.RetrofitInstance
import com.batarilo.vinylcollection.ui.home.recycle.RecordAdapterSearch
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException


@AndroidEntryPoint
class SearchFragment : Fragment(), RecordAdapterSearch.OnRecordListenerSearch {

    lateinit var navigationToggled: ActionBarDrawerToggle
    private lateinit var recordAdapterSearch: RecordAdapterSearch
   lateinit var viewCurrent: View
    private val viewModel:HomeViewModel by viewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewCurrent  = inflater.inflate(R.layout.fragment_search, container, false)
        setupRecyclerView()
        search("bo")
        return viewCurrent
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(navigationToggled.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() = viewCurrent.findViewById<RecyclerView>(R.id.rv_record)?.apply {
        recordAdapterSearch = RecordAdapterSearch(this@SearchFragment)
        adapter = recordAdapterSearch
        layoutManager = LinearLayoutManager(activity)

    }
    private fun search(term:String){
        lifecycleScope.launchWhenCreated {

            val result = try {
                RetrofitInstance.api.homeSearch(
                    RecordApiService.AUTH_KEY,
                    RecordApiService.AUTH_SECRET,
                    term
                )
            }
            catch (e: IOException){
                Log.d(tag, "EXCEPTION $e. You might not have an internet connection.")
                return@launchWhenCreated
            }
            catch (e: HttpException){
                Log.d(tag, "EXCEPTION $e")
                return@launchWhenCreated
            }

            if(result.isSuccessful && result.body()!=null){
                println("results")
                recordAdapterSearch.records = result.body()!!.results
            }
            else throw HttpException(result)
        }
    }

    override fun onRecordClicked(position: Int) {
        Toast.makeText(context, "Added to collection", Toast.LENGTH_SHORT).show()
        println(" "+ recordAdapterSearch.records[position].toString() + " clicked record")

    }

    override fun onCollectedClicked(position: Int) {
        val clickedRecord = recordAdapterSearch.records[position]
        viewModel.addRecordToCollection(clickedRecord)

    }

    override fun onAddToWishListClicked(position: Int) {
        Toast.makeText(context, "Added to wishlist", Toast.LENGTH_LONG).show()
        val clickedRecord = recordAdapterSearch.records[position]
        viewModel.addRecordToWishlist(clickedRecord)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}