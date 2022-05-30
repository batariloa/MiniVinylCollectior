package com.batarilo.vinylcollection.ui.search

import kotlin.concurrent.schedule
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.ui.info.InfoFragment
import com.batarilo.vinylcollection.ui.search.recycle.RecordAdapterSearch
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


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
        Navigation.findNavController(viewCurrent)
            .navigate(R.id.infoFragment, bundle)
    }

    override fun onCollectedClicked(position: Int) {
        viewModel.addRecordToCollection(position)
    }

    override fun onAddToWishListClicked(position: Int) {
        println("STAMPA SE OVO")
        viewModel.addRecordToWishlist(position)
    }


}