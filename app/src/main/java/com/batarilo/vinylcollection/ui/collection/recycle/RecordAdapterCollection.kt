package com.batarilo.vinylcollection.ui.collection.recycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.databinding.RecordRowCollectionBinding
import com.bumptech.glide.Glide

class RecordAdapterCollection(
    private val onRecordListener: OnRecordListener
) : Adapter<RecordAdapterCollection.RecordViewHolderSearch>() {


    private val diffCallback = object : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var records: List<Record>
        get() = differ.currentList
        set(value) { differ.submitList(value) }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolderSearch {


        return RecordViewHolderSearch(
            RecordRowCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRecordListener
        )

    }

    override fun onBindViewHolder(holderSearch: RecordViewHolderSearch, position: Int) {

        holderSearch.binding.apply {
            val item = records[position]
            tvTitle.text = item.title
            tvLabel.text = item.year
            tvFrom.text = item.country

            Glide.with(holderSearch.itemView.context)
                .load(item.thumb)
                .placeholder(R.drawable.empty_record)
                .into(imageRecord)


        }

    }

    override fun getItemCount(): Int {
        return records.size
    }


    class RecordViewHolderSearch(
        val binding: RecordRowCollectionBinding,
        private val onRecordListener: OnRecordListener
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener{
                onRecordListener.onRecordClicked(adapterPosition)
            }
            binding.btnRemoveFromCollection.setOnClickListener{
                onRecordListener.onRemoveFromCollectedClicked(adapterPosition)
            }
            binding.btnAddNoteCollection.setOnClickListener{
                onRecordListener.onAddToNotesClicked(adapterPosition)
            }


        }

    }


    interface OnRecordListener{
        fun onRecordClicked(position:Int)
        fun onRemoveFromCollectedClicked(position: Int)
        fun onAddToNotesClicked(position: Int)


    }




}


