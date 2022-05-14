package com.batarilo.vinylcollection.ui.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.databinding.RecordRowWishlistBinding
import com.bumptech.glide.Glide

class RecordAdapterWishlist(
    private val onRecordListener: OnRecordListenerWishlist
) : Adapter<RecordAdapterWishlist.RecordViewHolder>() {


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



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {


        return RecordViewHolder(
            RecordRowWishlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRecordListener
        )

    }



    override fun onBindViewHolder(holderSearch: RecordViewHolder, position: Int) {

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


    class RecordViewHolder(
        val binding: RecordRowWishlistBinding,
        private val onRecordListener: OnRecordListenerWishlist
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener{
                onRecordListener.onRecordClicked(adapterPosition)
            }
            binding.btnRemoveFromCollection.setOnClickListener{
                onRecordListener.onRemoveClicked(adapterPosition)
            }
            binding.btnAddNoteCollection.setOnClickListener{
                onRecordListener.onAddToNotesClicked(adapterPosition)
            }


        }



    }


    interface OnRecordListenerWishlist{
        fun onRecordClicked(position:Int)
        fun onRemoveClicked(position: Int)
        fun onAddToNotesClicked(position: Int)

    }




}


