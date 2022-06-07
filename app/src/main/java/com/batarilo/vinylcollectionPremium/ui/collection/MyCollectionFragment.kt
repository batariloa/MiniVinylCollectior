package com.batarilo.vinylcollectionPremium.ui.collection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.batarilo.vinylcollectionPremium.R
import com.batarilo.vinylcollectionPremium.ui.HomeActivity
import com.batarilo.vinylcollectionPremium.ui.collection.recycle.RecordAdapterCollection
import com.batarilo.vinylcollectionPremium.ui.info.InfoFragment


class MyCollectionFragment : Fragment(), RecordAdapterCollection.OnRecordListenerCollection {




    lateinit var viewCurrent:View
    val viewModel: MyCollectionViewModel by activityViewModels()
    lateinit var homeActivity :HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        // Inflate the layout for this fragment
        viewCurrent = inflater.inflate(R.layout.fragment_my_collection, container, false)


        val src =viewCurrent.findViewById<SearchView>(R.id.sv_record)

        src.queryHint = "Search collection.."

        src.setOnClickListener { src.isIconified = false }

        src.setQuery(viewModel.query.value, false)

        src.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {


                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.query.value = p0
                    viewModel.searchCollection()
                }
                return true
            }

        })

        activity?.let { viewModel.setupRecyclerView(viewCurrent, it, this) }
        viewModel.searchCollection()

        return viewCurrent
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is HomeActivity){
          homeActivity = context
        }
    }
    override fun onRecordClicked(position: Int) {
        val bundle = Bundle().also {
            it.putSerializable(InfoFragment.RECORD_PARAM,
                viewModel.recordAdapter.records[position].record)
        }
        homeActivity.let {
            Navigation.findNavController(it,R.id.fragment)
                .navigate(R.id.infoFragment, bundle)
        }


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





}