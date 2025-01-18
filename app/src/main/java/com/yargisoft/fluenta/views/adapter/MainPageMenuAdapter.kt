package com.yargisoft.fluenta.views.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yargisoft.fluenta.data.model.MainPageMenuItem
import com.yargisoft.fluenta.databinding.ItemMainPageMenuBinding
import javax.inject.Inject

class MainPageMenuAdapter @Inject constructor() : RecyclerView.Adapter<MainPageMenuAdapter.MainPageViewHolder>() {

    private var items: List<MainPageMenuItem> = emptyList()
    private var onItemClick: ((MainPageMenuItem) -> Unit)? = null

    inner class MainPageViewHolder(private val binding: ItemMainPageMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainPageMenuItem) {
            binding.tvTitle.text = item.title
            binding.ivIcon.setImageResource(item.iconResId)
            binding.root.setOnClickListener { onItemClick?.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageViewHolder {
        val binding = ItemMainPageMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<MainPageMenuItem>, onItemClick: (MainPageMenuItem) -> Unit) {
        items = newItems
        this.onItemClick = onItemClick
        notifyDataSetChanged()
    }
}
