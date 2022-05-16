package com.batarilo.vinylcollection.ui.home.recycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.databinding.RecordRowSearchBinding
import com.bumptech.glide.Glide

class RecordAdapterSearch(
    private val onRecordListenerSearch: OnRecordListenerSearch
) : Adapter<RecordAdapterSearch.RecordViewHolder>() {


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
                RecordRowSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRecordListenerSearch
        )

    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {

        holder.binding.apply {
            val item = records[position]
             rowTextViews.tvTitle.text = item.title
            rowTextViews.tvLabel.text = item.year
            rowTextViews.tvFrom.text = item.country

                Glide.with(holder.itemView.context)
                    .load(item.thumb)
                    .placeholder(R.drawable.empty_record)
                    .into(imageRecord)
        }

    }

    override fun getItemCount(): Int {
        return records.size
    }


    class RecordViewHolder(
        val binding: RecordRowSearchBinding,
        private val onRecordListenerSearch: OnRecordListenerSearch
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener{
                onRecordListenerSearch.onRecordClicked(adapterPosition)
            }
            binding.rowButtons.btnAddToCollection.setOnClickListener{
                onRecordListenerSearch.onCollectedClicked(adapterPosition)
            }
            binding.rowButtons.btnAddToWishlist.setOnClickListener{
                onRecordListenerSearch.onAddToWishListClicked(adapterPosition)
            }
        }

    }


    interface OnRecordListenerSearch{
        fun onRecordClicked(position:Int)
        fun onCollectedClicked(position: Int)
        fun onAddToWishListClicked(position: Int)
    }




}


