package com.batarilo.vinylcollection.ui.search.recycle

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

    private var clickedCollection = mutableSetOf<Int>()
    private var clickedWishList = mutableSetOf<Int>()

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
            onRecordListenerSearch,
            clickedCollection,
            clickedWishList,
            this
        )

    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {

        holder.binding.apply {
            val item = records[position]
             rowTextViews.tvTitle.text = item.title
            rowTextViews.tvLabel.text = item.year + " " + item.country
            rowTextViews.tvFrom.text = item.genre.toString().substring(1,item.genre.toString().length-1 )


                Glide.with(holder.itemView.context)
                    .load(item.thumb)
                    .placeholder(R.drawable.empty_record)
                    .into(imageRecord)

            if(clickedCollection.contains(position))
                rowButtons.btnAddToCollection.setImageResource(R.drawable.ic_baseline_playlist_add_check_24)
            else
                rowButtons.btnAddToCollection.setImageResource(R.drawable.ic_baseline_playlist_add_24)

            if(clickedWishList.contains(position))
                rowButtons.btnAddToWishlist.setImageResource(R.drawable.ic_star_filled)
            else
                rowButtons.btnAddToWishlist.setImageResource(R.drawable.ic_baseline_star_border_35)





        }
    }

    override fun getItemCount(): Int {
        return records.size
    }
    fun updateItems(updatedItems: List<Record>) {
        records = updatedItems
        notifyDataSetChanged()
    }


    class RecordViewHolder(
        val binding: RecordRowSearchBinding,
        private val onRecordListenerSearch: OnRecordListenerSearch,
        private val clickedCollection: MutableSet<Int>,
        private val clickedWishlist: MutableSet<Int>,
        private val recordAdapterSearch: RecordAdapterSearch
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener{
                onRecordListenerSearch.onRecordClicked(bindingAdapterPosition)
            }
            binding.rowButtons.btnAddToCollection.setOnClickListener{
                onRecordListenerSearch.onCollectedClicked(bindingAdapterPosition)
                clickedCollection.add(bindingAdapterPosition)
                recordAdapterSearch.notifyDataSetChanged()

            }
            binding.rowButtons.btnAddToWishlist.setOnClickListener{
                onRecordListenerSearch.onAddToWishListClicked(bindingAdapterPosition)
                clickedWishlist.add(bindingAdapterPosition)
                recordAdapterSearch.notifyDataSetChanged()


            }
        }

    }


    interface OnRecordListenerSearch{
        fun onRecordClicked(position:Int)
        fun onCollectedClicked(position: Int)
        fun onAddToWishListClicked(position: Int)
    }




}


