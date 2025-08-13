package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.PlanListResponse.ResponseDetails.Limited
import com.app.reelshort.Model.PlanListResponse.ResponseDetails.Unlimited
import com.app.reelshort.Utils.gone
import com.app.reelshort.databinding.ItemEpisodeBinding
import com.app.reelshort.databinding.ItemRefillPlanBinding
import com.app.reelshort.databinding.ItemSubListBinding

class PlanRefillAdapter(private val items: List<Limited?>, val onClickListener: (Limited) -> Unit) :
    RecyclerView.Adapter<PlanRefillAdapter.PlanRefillViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanRefillViewHolder {
        return PlanRefillViewHolder(ItemRefillPlanBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun getItemCount() = items.size

    inner class PlanRefillViewHolder(val binding: ItemRefillPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Limited) {


            binding.coin.text = (item.coin ?: "").toString()

            if (item.extraCoin == null) {
                binding.extraCoin.isVisible = false
            } else {

                binding.extraCoin.text = item.extraCoin
            }

            item.amount?.let {
                binding.amount.text = "$ ${item.amount}"
            }
            if (item.discountPercentage == null) {
                binding.discount.gone()
            } else {
                binding.discount.text = item.discountPercentage + "% Discount"
            }
            binding.root.setOnClickListener {
                onClickListener(item)
            }
        }
    }


    override fun onBindViewHolder(holder: PlanRefillViewHolder, position: Int) {
        val item = items[position]
        item?.let {
            holder.bind(item)
        }
    }
}