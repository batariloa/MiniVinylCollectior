package com.batarilo.vinylcollection.ui.search.recycle

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.databinding.RecordRowSearchBinding
import com.batarilo.vinylcollection.interactors.record_list.RecordExistsInCollection
import com.batarilo.vinylcollection.interactors.record_list.RecordExistsInWishlist
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecordAdapterSearch(
    private val onRecordListenerSearch: OnRecordListenerSearch,
    private val recordExistsInCollection: RecordExistsInCollection,
    private val recordExistsInWishlist: RecordExistsInWishlist
) : Adapter<RecordAdapterSearch.RecordViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<RecordInList>() {
        override fun areItemsTheSame(oldItem: RecordInList, newItem: RecordInList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RecordInList, newItem: RecordInList): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemId(position: Int): Long {
        return records[position].record.id.toLong()
    }


    private val differ = AsyncListDiffer(this, diffCallback)
    var records: List<RecordInList>
        get() = differ.currentList
        set(value) { differ.submitList(value) }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {

        return RecordViewHolder(
                RecordRowSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,

            ),

            onRecordListenerSearch,

        )

    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {


        holder.binding.apply {
            val item = records[position]

           val year = if(item.record.year==null || item.record.year==""){
                "Year N/A"
            } else item.record.year

             rowTextViews.tvTitle.text = item.record.title
            rowTextViews.tvLabel.text = "$year ${item.record.country}"
            rowTextViews.tvFrom.text = item.record.genre.toString().substring(1,item.record.genre.toString().length-1 )

            Glide.with(holder.itemView.context)
                .load(item.record.cover_image)
                .placeholder(R.drawable.empty_record)
                .into(imageRecord)

            //checks if item is in in collection or wishlist
            recordInCollectionExists(item.record,holder)
            recordInWishlistExists(item.record,holder)

        }
    }

    override fun getItemCount(): Int {
        return records.size
    }

    private fun recordInCollectionExists(record: Record, holder: RecordViewHolder) {

        recordExistsInCollection.execute(record.id).onEach { dataState ->
            dataState.data?.let { result ->

                holder.binding.apply {
                    if (result) {
                        rowButtons.btnAddToCollection.setImageResource(R.drawable.ic_baseline_playlist_add_check_24)

                    } else {
                        rowButtons.btnAddToCollection.setImageResource(R.drawable.ic_baseline_playlist_add_24)
                    }
                }
            }
        }.launchIn(scope = CoroutineScope(Dispatchers.IO))
    }

    private fun recordInWishlistExists(record: Record, holder: RecordViewHolder){

        recordExistsInWishlist.execute(record.id).onEach { dataState->
            dataState.data?.let { result ->

                holder.binding.apply {
                    if (result)
                        rowButtons.btnAddToWishlist.setImageResource(R.drawable.ic_star_filled)
                    else {
                        rowButtons.btnAddToWishlist.setImageResource(R.drawable.ic_baseline_star_border_35)
                    } } } }.launchIn(scope = CoroutineScope(Dispatchers.IO)) }


    class RecordViewHolder(
        val binding: RecordRowSearchBinding,
        private val onRecordListenerSearch: OnRecordListenerSearch,
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.imageRecord.setOnClickListener{
                onRecordListenerSearch.onRecordClicked(bindingAdapterPosition)
            }
            binding.rowTextViews.rowTextLayout.setOnClickListener{
                onRecordListenerSearch.onRecordClicked(bindingAdapterPosition)
            }
            binding.rowButtons.btnAddToCollection.setOnClickListener{
                onRecordListenerSearch.onCollectedClicked(bindingAdapterPosition)

            }
            binding.rowButtons.btnAddToWishlist.setOnClickListener{
                onRecordListenerSearch.onAddToWishListClicked(bindingAdapterPosition)



            }
        }

    }


    interface OnRecordListenerSearch{
        fun onRecordClicked(position:Int)
        fun onCollectedClicked(position: Int)
        fun onAddToWishListClicked(position: Int)
    }




}


