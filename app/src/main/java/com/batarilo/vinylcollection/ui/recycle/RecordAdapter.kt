package com.batarilo.vinylcollection.ui.recycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.databinding.RecordRowBinding
import com.squareup.picasso.Picasso

class RecordAdapter()
    : Adapter<RecordAdapter.RecordViewHolder>() {


    class RecordViewHolder(val binding: RecordRowBinding) : RecyclerView.ViewHolder(binding.root)

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
            RecordRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        )

    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {

        holder.binding.apply {
            val item = records[position]
            tvTitle.text = item.title
            tvLabel.text = item.year
            tvFrom.text = item.genre?.toString()
            Picasso.with(holder.itemView.context).load(item.cover_image).into(imageRecord)
        }

    }

    override fun getItemCount(): Int {
       return records.size
    }


}


