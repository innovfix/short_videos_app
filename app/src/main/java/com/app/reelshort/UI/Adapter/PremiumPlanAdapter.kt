package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Model.PremiumPlans
import com.app.reelshort.Model.Shorts
import com.app.reelshort.R
import com.app.reelshort.databinding.AdapterPremiumPlanBinding
import com.app.reelshort.databinding.AdapterShortBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class PremiumPlanAdapter(
    private val shorts: List<PremiumPlans>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemHolder = ItemHolder(
            AdapterPremiumPlanBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        return itemHolder
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder: ItemHolder = holderParent as ItemHolder
        val premiumPlan: PremiumPlans? = shorts[position]

        holder.binding.tvPremiumPlan.text = premiumPlan?.title
        holder.binding.tvOriginalCost.text = BaseApplication.getInstance().getString(R.string.rupee_text,premiumPlan?.originalCost)
        holder.binding.tvCurrentCost.text = BaseApplication.getInstance().getString(R.string.rupee_text,premiumPlan?.currentCost)
        holder.binding.tvTerm.text = premiumPlan?.term
        holder.binding.tvOffer.text = premiumPlan?.offer
    }

    override fun getItemCount(): Int {
        return shorts.size
    }

    internal class ItemHolder(val binding: AdapterPremiumPlanBinding) :
        RecyclerView.ViewHolder(binding.root)
}
