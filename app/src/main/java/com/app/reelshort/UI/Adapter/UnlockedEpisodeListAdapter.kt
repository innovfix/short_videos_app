package com.app.reelshort.UI.Adapter

import android.R.attr.text
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.TransactionListResponse
import com.app.reelshort.Model.UnlockedEpisodeListResponse
import com.app.reelshort.R
import com.app.reelshort.Utils.toFormattedDateTime
import com.app.reelshort.databinding.ItemTransactionBinding
import com.app.reelshort.databinding.ItemUnlockedListBinding

class UnlockedEpisodeListAdapter(
    private val items: List<UnlockedEpisodeListResponse.ResponseDetail>,
    val onClickListener: (String) -> Unit,
) : RecyclerView.Adapter<UnlockedEpisodeListAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            ItemUnlockedListBinding.inflate(
                LayoutInflater.from(parent.context),
                null,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    inner class EpisodeViewHolder(val binding: ItemUnlockedListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UnlockedEpisodeListResponse.ResponseDetail) {

            binding.title.text = "${item.title}"
            binding.coin.text = "-${item.coin}"
            binding.episode.text = "Episode ${item.episodeNumber}"
            binding.date.text = "${item.createdAt?.toFormattedDateTime()}"
            binding.root.setOnClickListener {
                onClickListener(item.id.toString())
            }
        }
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

