package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Model.Shorts
import com.app.reelshort.databinding.AdapterShortBinding
import com.app.reelshort.databinding.ItemReelBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


class ReelsAdapter(
    var shorts: List<Shorts>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var player: ExoPlayer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemHolder = ItemHolder(
            ItemReelBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        return itemHolder
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        var holder: ItemHolder = holderParent as ItemHolder
        val short: Shorts = shorts[position]
        player = ExoPlayer.Builder(BaseApplication.getInstance()).build()
        holder.binding.playerView.player = player

        val mediaItem = MediaItem.fromUri(short.videoUrl)
        player.setMediaItem(mediaItem)

        player.prepare()
        player.playWhenReady = true

    }

    fun setSaveStatus(position: Int, status:Boolean){
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return shorts.size
    }

    internal class ItemHolder(val binding: ItemReelBinding) :
        RecyclerView.ViewHolder(binding.root)
}
