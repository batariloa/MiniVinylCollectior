package com.batarilo.vinylcollection.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InfoFragment : Fragment() {
    lateinit var viewCurrent:View
    private var record:Record? = null
    private val viewModel: InfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

           if(arguments?.getSerializable(RECORD_PARAM) is Record)
               record = arguments?.getSerializable(RECORD_PARAM) as Record


            lifecycleScope.launchWhenCreated {
                record?.let { viewModel.addRecordToHistory(it) }
            }
        }

    }

    private fun initializeView(){

        Glide.with(viewCurrent.context)
            .load(record?.thumb)
            .placeholder(R.drawable.empty_record)
            .into(viewCurrent.findViewById(R.id.image_record))

        viewCurrent.findViewById<TextView>(R.id.tv_title_info).text = record?.title
        viewCurrent.findViewById<TextView>(R.id.tv_year_info).text = record?.year
        viewCurrent.findViewById<TextView>(R.id.tv_country_info).text = record?.country
        viewCurrent.findViewById<TextView>(R.id.tv_genre_info).text = record?.genre.toString()
        viewCurrent.findViewById<TextView>(R.id.tv_label_info).text = record?.label.toString()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewCurrent = inflater.inflate(R.layout.fragment_info, container, false)
        initializeView()


        return viewCurrent
    }

    companion object {
        const val RECORD_PARAM = "record"

        @JvmStatic
        fun newInstance(record: Record) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(RECORD_PARAM, record )
                }
            }
    }
}