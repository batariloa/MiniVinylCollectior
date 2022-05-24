package com.batarilo.vinylcollection.ui.collection.recycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.databinding.RecordRowCollectionBinding
import com.bumptech.glide.Glide

class RecordAdapterCollection(
    private val onRecordListener: OnRecordListenerCollection
) : Adapter<RecordAdapterCollection.RecordViewHolderSearch>() {


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
            onRecordListener
        )

    }



    override fun onBindViewHolder(holderSearch: RecordViewHolderSearch, position: Int) {

        holderSearch.binding.apply {
            val item = records[position]
            
       rowTextViews.tvTitle.text = item.record.title
        rowTextViews.tvLabel.text = item.record.year
        rowTextViews.tvFrom.text = item.record.country

            Glide.with(holderSearch.itemView.context)
                .load(item.record.thumb)
                .placeholder(R.drawable.empty_record)
                .into(imageRecord)

            println("IS IT NULL ${item.record}.")

            if(item.record.note!=null){
                rowButtons.btnAddNoteCollection.setImageResource(R.drawable.ic_baseline_sticky_note_set)
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
            binding.root.setOnClickListener{
                onRecordListener.onRecordClicked(adapterPosition)
            }
            binding.rowButtons.btnRemoveFromCollection.setOnClickListener{
                onRecordListener.onRemoveClicked(adapterPosition)
            }
            binding.rowButtons.btnAddNoteCollection.setOnClickListener{
                onRecordListener.onAddToNotesClicked(adapterPosition)
            }


        }



    }


    interface OnRecordListenerCollection{
        fun onRecordClicked(position:Int)
        fun onRemoveClicked(position: Int)
        fun onAddToNotesClicked(position: Int)

    }




}


