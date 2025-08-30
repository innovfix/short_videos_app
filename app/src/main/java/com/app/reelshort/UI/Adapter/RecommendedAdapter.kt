package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.CommonInfo
import com.app.reelshort.R
import com.app.reelshort.databinding.ItemEpisodeBinding
import com.app.reelshort.databinding.ItemRecommendedBinding
import com.bumptech.glide.Glide

class RecommendedAdapter(
    private val items: List<CommonInfo?>,
    val onClickListener: (String) -> Unit,
) : RecyclerView.Adapter<RecommendedAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            ItemRecommendedBinding.inflate(
                LayoutInflater.from(parent.context),
                null,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    inner class EpisodeViewHolder(val binding: ItemRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommonInfo) {
            binding.root.setOnClickListener {
                onClickListener(item.id.toString())
            }
            Glide.with(binding.thumbnail)
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

