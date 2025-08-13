package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.CommonInfo
import com.app.reelshort.Model.CommonInfoBase
import com.app.reelshort.Utils.CommonsKt.formatNumber
import com.app.reelshort.Utils.toBoolean
import com.app.reelshort.Utils.visible
import com.app.reelshort.databinding.ItemEpisodeBinding
import com.bumptech.glide.Glide

class CommonInfoAdapter(private val items: List<CommonInfo>, val onClickListener: (String) -> Unit) :
    RecyclerView.Adapter<CommonInfoAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun getItemCount() = items.size

    inner class EpisodeViewHolder(val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommonInfo) {
            binding.root.setOnClickListener {
                onClickListener(item.id.toString())
            }
            if (item.isFree?.toBoolean == true) {
                binding.freeLabel.visible()
            }

            binding.textTitle.text = item.title
            binding.textTag.text = item.tagsName
            binding.categoryName.text = item.categoryName
            binding.textViews.text = item.views?.formatNumber()

            Glide.with(binding.imagePoster.context)
                .load(item.thumbnail)
                .into(binding.imagePoster)


        }
    }


    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = items[position]
        item?.let {
            holder.bind(item)
        }
    }
}

