package com.batarilo.vinylcollectionPremium.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.batarilo.vinylcollectionPremium.R
import com.batarilo.vinylcollectionPremium.ui.info.InfoFragment
import com.batarilo.vinylcollectionPremium.ui.search.recycle.RecordAdapterSearch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class SearchFragment : Fragment(), RecordAdapterSearch.OnRecordListenerSearch {

    private lateinit var navigationToggled: ActionBarDrawerToggle
    private lateinit var viewCurrent: View
    private val viewModel:SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewCurrent  = inflater.inflate(R.layout.fragment_search, container, false)
        activity?.let { viewModel.setupRecyclerView(viewCurrent, this@SearchFragment, it) }


        val src =viewCurrent.findViewById<SearchView>(R.id.sv_record)

        src.setOnClickListener { src.isIconified = false }

        src.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.query.value = p0
                if (p0 != null) { viewModel.newSearch() }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            } })


        //this is critical and should be changed
        lifecycleScope.launchWhenCreated {
            searchFirst()

        }

        return viewCurrent
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(navigationToggled.onOptionsItemSelected(item)){ return true }

        return super.onOptionsItemSelected(item)
    }




    override fun onRecordClicked(position: Int) {

        val bundle = Bundle().also {
           it.putSerializable(InfoFragment.RECORD_PARAM,viewModel.recordAdapterSearch.records[position].record)
        }
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.infoFragment, bundle)
        }
    }

    override fun onCollectedClicked(position: Int) {
        viewModel.addRecordToCollection(position)
    }

    override fun onAddToWishListClicked(position: Int) {
        viewModel.addRecordToWishlist(position)
    }

    private suspend fun searchFirst(){

        delay(300)
        viewModel.connectivityManager.connectionLiveData.observe(viewLifecycleOwner, Observer {
            if(it==true){
                viewModel.newSearch()
            }
        })
    }
}