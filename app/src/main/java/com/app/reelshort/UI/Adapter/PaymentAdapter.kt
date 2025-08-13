package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.PaymentOptionResponse
import com.app.reelshort.databinding.ItemPaymentOptionBinding

class PaymentAdapter(
    val items: List<PaymentOptionResponse.ResponseDetail>,
    val onClickListener: (PaymentOptionResponse.ResponseDetail) -> Unit,
) :
    RecyclerView.Adapter<PaymentAdapter.TagsAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAdapter {
        return TagsAdapter(
            ItemPaymentOptionBinding.inflate(
                LayoutInflater.from(parent.context),
                null,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    inner class TagsAdapter(val binding: ItemPaymentOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PaymentOptionResponse.ResponseDetail) {
            binding.root.setOnClickListener {
                onClickListener(item)
            }
            binding.name.text = item.name
        }
    }

    override fun onBindViewHolder(holder: TagsAdapter, position: Int) {
        holder.bind(items[position])
    }
}