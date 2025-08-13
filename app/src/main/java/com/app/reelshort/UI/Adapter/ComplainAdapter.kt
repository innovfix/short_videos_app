package com.app.reelshort.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reelshort.Model.TicketResponse
import com.app.reelshort.R
import com.app.reelshort.Utils.toFormattedDateTime
import com.app.reelshort.databinding.ItemComplainListBinding
import com.app.reelshort.databinding.ItemPaymentOptionBinding

class ComplainAdapter(
    var items: List<TicketResponse.ResponseDetail>,
    val onClickListener: (TicketResponse.ResponseDetail) -> Unit,
) :
    RecyclerView.Adapter<ComplainAdapter.TagsAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAdapter {
        return TagsAdapter(
            ItemComplainListBinding.inflate(
                LayoutInflater.from(parent.context),
                null,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    inner class TagsAdapter(val binding: ItemComplainListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketResponse.ResponseDetail) {

            binding.ticketId.text = "Ticket ID: ${item.ticketId}"
            binding.tvReason.text = "Reason: ${item.name}"
            binding.tvName.text = "Name: ${item.reason}"
            binding.tvDate.text = "Date: ${item.createdAt?.toString()?.toFormattedDateTime()}"


            binding.tvStatus.text = if (item.status == "Pending") "Pending" else "Completed"
            binding.tvStatus.setBackgroundResource(
                if (item.status == "Pending") R.drawable.bg_status_pending
                else R.drawable.bg_status_completed
            )
            binding.root.setOnClickListener {
                onClickListener(item)
            }
        }
    }

    override fun onBindViewHolder(holder: TagsAdapter, position: Int) {
        holder.bind(items[position])
    }
}