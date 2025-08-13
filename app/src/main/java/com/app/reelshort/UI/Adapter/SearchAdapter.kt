package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.CommonInfo
import com.app.reelshort.Model.CommonInfoBase
import com.app.reelshort.databinding.ItemEpisodeBinding
import com.app.reelshort.databinding.ItemSearchBinding
import com.bumptech.glide.Glide

class SearchAdapter(private val items: List<CommonInfo>, val onClickListener: (CommonInfo) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                null,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    inner class EpisodeViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommonInfo) {
            binding.root.setOnClickListener {
                onClickListener(item)
            }
            binding.title.text = item.title
            binding.tagsName.text = item.tagsName
            Glide.with(binding.thumbnail.context)
                .load(item.thumbnail)
                .into(binding.thumbnail)
        }
    }


    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = items[position]
        item?.let {
            holder.bind(item)
        }
    }
}

