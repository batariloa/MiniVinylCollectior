package com.batarilo.vinylcollectionPremium.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batarilo.vinylcollectionPremium.R
import com.batarilo.vinylcollectionPremium.ui.collection.recycle.RecordAdapterCollection
import com.batarilo.vinylcollectionPremium.ui.info.InfoFragment


class HistoryFragment : Fragment(), RecordAdapterCollection.OnRecordListenerCollection {



    private lateinit var viewCurrent:View
    val viewModel: HistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewCurrent = inflater.inflate(R.layout.fragment_history, container, false)
        setupRecyclerView()
        loadHistory()


        return viewCurrent
    }


    private fun loadHistory(){
        viewModel.readAllFromHistory()
        setupRecyclerView()
    }



    private fun setupRecyclerView() = viewCurrent.findViewById<RecyclerView>(R.id.rv_record)?.apply {
        viewModel.recordAdapter = RecordAdapterCollection(this@HistoryFragment)
        adapter = viewModel.recordAdapter
        layoutManager = LinearLayoutManager(activity)

    }
    override fun onRecordClicked(position: Int) {
        val bundle = Bundle().also {
            it.putSerializable(InfoFragment.RECORD_PARAM,
                viewModel.recordAdapter.records[position].record)
        }
        Navigation.findNavController(viewCurrent)
            .navigate(R.id.infoFragment, bundle)

    }

    override fun onRemoveClicked(position: Int) {
        viewModel.deleteRecord(position)
     }

    override fun onAddToNotesClicked(position: Int) {
        viewModel.setRecordNote(viewCurrent.context, position).apply {
            show()
            setOnDismissListener{

                viewModel.recordAdapter.notifyItemChanged(position)
             }
        }
    }



    companion object {

        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


}