package com.batarilo.vinylcollection.ui.home.recycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.databinding.RecordRowBinding
import com.bumptech.glide.Glide

class RecordAdapter(
    private val onRecordListener: OnRecordListener
) : Adapter<RecordAdapter.RecordViewHolder>() {


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
            ),
            onRecordListener
        )

    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {

        holder.binding.apply {
            val item = records[position]
            tvTitle.text = item.title
            tvLabel.text = item.year
            tvFrom.text = item.country?.toString()

            Glide.with(holder.itemView.context)
                .load(item.thumb)
                .placeholder(androidx.constraintlayout.widget.R.drawable.abc_ic_menu_paste_mtrl_am_alpha)
                .into(imageRecord)


        }

    }

    override fun getItemCount(): Int {
        return records.size
    }


    class RecordViewHolder(
        val binding: RecordRowBinding,
        val onRecordListener: OnRecordListener
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener{
                onRecordListener.onRecordClicked(adapterPosition)
            }
            binding.btnAddToCollection.setOnClickListener{
                onRecordListener.onCollectedClicked(adapterPosition)
            }
        }

    }


    interface OnRecordListener{
        fun onRecordClicked(position:Int)
        fun onCollectedClicked(position: Int)
    }


}


