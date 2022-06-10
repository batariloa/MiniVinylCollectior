package com.batarilo.vinylcollectionPremium.ui.collection.recycle

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.batarilo.vinylcollectionPremium.R
import com.batarilo.vinylcollectionPremium.data.model.RecordInList
import com.batarilo.vinylcollectionPremium.databinding.RecordRowCollectionBinding
import com.bumptech.glide.Glide

class RecordAdapterCollection(
    private val onRecordListener: OnRecordListenerCollection
) : Adapter<RecordAdapterCollection.RecordViewHolderSearch>() {


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    private val diffCallback = object : DiffUtil.ItemCallback<RecordInList>() {
        override fun areItemsTheSame(oldItem: RecordInList, newItem: RecordInList): Boolean {
            return oldItem.record.id== newItem.record.id
        }

        override fun areContentsTheSame(oldItem: RecordInList, newItem: RecordInList): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var records: List<RecordInList>
        get() = differ.currentList
        set(value) { differ.submitList(value) }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolderSearch {


        return RecordViewHolderSearch(
            RecordRowCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRecordListener,

        )
    }



    override fun onBindViewHolder(holderSearch: RecordViewHolderSearch, position: Int) {

        holderSearch.binding.apply {
            val item = records[position]

            val year = if(item.record.year==null || item.record.year==""){
                "Year N/A"
            } else item.record.year

            rowTextViews.tvTitle.text = item.record.title
            rowTextViews.tvLabel.text = "$year ${item.record.country}"
            rowTextViews.tvFrom.text = item.record.country

            Glide.with(holderSearch.itemView.context)
                .load(item.record.cover_image)
                .placeholder(R.drawable.empty_record)
                .into(imageRecord)

            println("IS IT NULL ${item.record}.")


            if(!item.record.note.isNullOrEmpty()){
                rowButtons.btnAddNoteCollection.setImageResource(R.drawable.ic_baseline_sticky_note_set)
            }
            else{
                rowButtons.btnAddNoteCollection.setImageResource(R.drawable.ic_baseline_sticky_note)

            }


        }


    }

    override fun getItemCount(): Int {
        return records.size
    }


    class RecordViewHolderSearch(
        val binding: RecordRowCollectionBinding,
        private val onRecordListener: OnRecordListenerCollection
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.imageRecord.setOnClickListener{
                onRecordListener.onRecordClicked(bindingAdapterPosition)
            }
            binding.rowTextViews.rowTextLayout.setOnClickListener{
                onRecordListener.onRecordClicked(bindingAdapterPosition)
            }
            binding.rowButtons.btnRemoveFromCollection.setOnClickListener{
                onRecordListener.onRemoveClicked(bindingAdapterPosition)
            }
            binding.rowButtons.btnAddNoteCollection.setOnClickListener{
                onRecordListener.onAddToNotesClicked(bindingAdapterPosition)
            }


        }



    }


    interface OnRecordListenerCollection{
        fun onRecordClicked(position:Int)
        fun onRemoveClicked(position: Int)
        fun onAddToNotesClicked(position: Int)

    }




}

