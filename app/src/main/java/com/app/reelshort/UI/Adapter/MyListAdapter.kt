package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Model.PremiumPlans
import com.app.reelshort.Model.Shorts
import com.app.reelshort.R
import com.app.reelshort.databinding.AdapterMyListBinding
import com.app.reelshort.databinding.AdapterPremiumPlanBinding
import com.app.reelshort.databinding.AdapterShortBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class MyListAdapter(
    private val shorts: List<Shorts>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemHolder = ItemHolder(
            AdapterMyListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        return itemHolder
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder: ItemHolder = holderParent as ItemHolder
        val short: Shorts = shorts[position]

        Glide.with(BaseApplication.getInstance()).load(short?.thumbnailUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .into(holder.binding.ivThumbnail)
        holder.binding.tvTitle.text = short?.title
        holder.binding.tvDescription.text = short?.description
    }

    override fun getItemCount(): Int {
        return shorts.size
    }

    internal class ItemHolder(val binding: AdapterMyListBinding) :
        RecyclerView.ViewHolder(binding.root)
}
