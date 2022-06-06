package com.batarilo.vinylcollection.ui.wishlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.databinding.RecordRowWishlistBinding
import com.bumptech.glide.Glide

class RecordAdapterWishlist(
    private val onRecordListener: OnRecordListenerWishlist
) : Adapter<RecordAdapterWishlist.RecordViewHolder>() {


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



    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holderSearch: RecordViewHolder, position: Int) {

        holderSearch.binding.apply {
            val item = records[position]

            val year = if(item.record.year==null || item.record.year==""){
                "Year N/A"
            } else item.record.year

            textviews.tvTitle.text = item.record.title
            textviews.tvLabel.text = "$year ${item.record.country}"
            textviews.tvFrom.text = item.record.country

            Glide.with(holderSearch.itemView.context)
                .load(item.record.cover_image)
                .placeholder(R.drawable.empty_record)
                .into(imageRecord)



            if(item.record.note!=null || item.record.note==""){
                rowButtons.btnAddNote.setImageResource(R.drawable.ic_baseline_sticky_note_set)
            }


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
            binding.imageRecord.setOnClickListener{
                onRecordListener.onRecordClicked(bindingAdapterPosition)
            }
            binding.textviews.rowTextLayout.setOnClickListener{
                onRecordListener.onRecordClicked(bindingAdapterPosition)
            }
            binding.rowButtons.btnRemoveFromWishlist.setOnClickListener{
                onRecordListener.onRemoveClicked(bindingAdapterPosition)
            }
            binding.rowButtons.btnAddNote.setOnClickListener{
                onRecordListener.onAddToNotesClicked(bindingAdapterPosition)
            } } }


    interface OnRecordListenerWishlist{
        fun onRecordClicked(position:Int)
        fun onRemoveClicked(position: Int)
        fun onAddToNotesClicked(position: Int)

    }




}

