package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.CommonInfo
import com.app.reelshort.Model.EpisodeListResponse
import com.app.reelshort.databinding.ItemEpisodeBinding
import com.app.reelshort.databinding.ItemTagsNameBinding

class TagsAdapter(
    val items: List<EpisodeListResponse.ResponseDetails.Series.Tag>,
    val onClickListener: (String, String) -> Unit,
) :
    RecyclerView.Adapter<TagsAdapter.TagsAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAdapter {
        return TagsAdapter(ItemTagsNameBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun getItemCount() = items.size

    inner class TagsAdapter(val binding: ItemTagsNameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EpisodeListResponse.ResponseDetails.Series.Tag) {
            binding.root.setOnClickListener {
                onClickListener(item.id.toString(), item.name.toString())
            }
            binding.name.text = item.name
        }
    }


    override fun onBindViewHolder(holder: TagsAdapter, position: Int) {
        val item = items[position]
        item.let {
            holder.bind(item)
        }
    }

}