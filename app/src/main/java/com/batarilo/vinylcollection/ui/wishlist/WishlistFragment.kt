package com.batarilo.vinylcollection.ui.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.ui.collection.MyCollectionViewModel
import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection
import com.batarilo.vinylcollection.ui.dialog.NoteDialog
import com.batarilo.vinylcollection.ui.info.InfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment(), RecordAdapterWishlist.OnRecordListenerWishlist {

    lateinit var viewCurrent:View
    lateinit var recordAdapter:RecordAdapterWishlist
    val viewModel: WishlistViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewCurrent = inflater.inflate(R.layout.fragment_my_collection, container, false)
        setupRecyclerView()
        loadWishList()
        return viewCurrent
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String){

        }

    }

    override fun onRecordClicked(position: Int) {
        val bundle = Bundle().also {
            it.putSerializable(InfoFragment.RECORD_PARAM,recordAdapter.records[position].record)
        }
        Navigation.findNavController(viewCurrent)
            .navigate(R.id.infoFragment, bundle)

    }

    override fun onRemoveClicked(position: Int) {
        viewModel.removeRecord(recordAdapter.records[position])
     }

    override fun onAddToNotesClicked(position: Int) {
        NoteDialog(viewCurrent.context, recordAdapter.records[position].record, viewModel.recordRepository).apply {
            show()
            setOnDismissListener{
                setupRecyclerView()
            }
        }
    }
    private fun loadWishList(){
        viewModel.loadWishList().observe(viewLifecycleOwner, Observer {
            recordAdapter.records = it
        })
    }

    private fun setupRecyclerView() = viewCurrent.findViewById<RecyclerView>(R.id.rv_record)?.apply {
        recordAdapter = RecordAdapterWishlist(this@WishlistFragment)
        adapter = recordAdapter
        layoutManager = LinearLayoutManager(activity)

    }
}