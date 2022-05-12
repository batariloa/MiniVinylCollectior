package com.batarilo.vinylcollection.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "record_param"


class ChooseSearchTypeFragment : Fragment() {
    private var param1: Record? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_search_type, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() {

        }

    }
}