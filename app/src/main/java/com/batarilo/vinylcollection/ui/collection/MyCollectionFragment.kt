package com.batarilo.vinylcollection.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection
import com.batarilo.vinylcollection.ui.dialog.NoteDialog
import com.batarilo.vinylcollection.ui.info.InfoFragment


class MyCollectionFragment : Fragment(), RecordAdapterCollection.OnRecordListenerCollection {



    lateinit var recordAdapter: RecordAdapterCollection
    lateinit var viewCurrent:View
    val viewModel: MyCollectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewCurrent = inflater.inflate(R.layout.fragment_my_collection, container, false)
        setupRecyclerView()
        loadCollection()
        return viewCurrent
    }


    private fun loadCollection(){
       viewModel.readAllFromCollection().observe(viewLifecycleOwner, Observer {
           recordAdapter.records = it
       })
    }


    private fun setupRecyclerView() = viewCurrent.findViewById<RecyclerView>(R.id.rv_record)?.apply {
        recordAdapter = RecordAdapterCollection(this@MyCollectionFragment)
        adapter = recordAdapter
        layoutManager = LinearLayoutManager(activity)

    }
    override fun onRecordClicked(position: Int) {
        val bundle = Bundle().also {
            it.putSerializable(InfoFragment.RECORD_PARAM,recordAdapter.records[position].record)
        }
        Navigation.findNavController(viewCurrent)
            .navigate(R.id.infoFragment, bundle)


    }

    override fun onRemoveClicked(position: Int) {
        viewModel.removeRecordFromCollection(recordAdapter.records[position])
     }

    override fun onAddToNotesClicked(position: Int) {
        NoteDialog(viewCurrent.context, recordAdapter.records[position].record, viewModel.recordRepository).apply {
            show()
            setOnDismissListener{
                setupRecyclerView()
            }
        }

    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyCollectionFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


}