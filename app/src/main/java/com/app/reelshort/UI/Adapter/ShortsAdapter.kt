package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Model.Shorts
import com.app.reelshort.R
import com.app.reelshort.callbacks.OnItemSelectionListener
import com.app.reelshort.databinding.AdapterShortBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class ShortsAdapter(
    private val shorts: List<Shorts>,
    val onItemSelectionListener: OnItemSelectionListener<Shorts>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemHolder = ItemHolder(
            AdapterShortBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        return itemHolder
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder: ItemHolder = holderParent as ItemHolder
        val short: Shorts = shorts[position]
        holder.binding.main.setOnClickListener {
            onItemSelectionListener.onItemSelected(short)
        }
        holder.binding.tvTitle.text = short.title
        Glide.with(BaseApplication.getInstance()).load(short.thumbnailUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24))).into(holder.binding.ivShort)

    }

    override fun getItemCount(): Int {
        return shorts.size
    }

    internal class ItemHolder(val binding: AdapterShortBinding) :
        RecyclerView.ViewHolder(binding.root)
}
