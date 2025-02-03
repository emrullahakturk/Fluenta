package com.yargisoft.fluenta.views.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yargisoft.fluenta.data.model.Favorite
import com.yargisoft.fluenta.databinding.ItemFavoriteCommonWordsBinding
import javax.inject.Inject

class CommonWordsFavoritesAdapter @Inject constructor() :
    RecyclerView.Adapter<CommonWordsFavoritesAdapter.FavoriteWordViewHolder>() {

    private var words: List<Favorite> = emptyList()
    private var onClickBtnRemove: ((word: String) -> Unit)? = null

    inner class FavoriteWordViewHolder(private val binding: ItemFavoriteCommonWordsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Favorite) {
            binding.tvWord.text = word.word
            binding.tvEnExample.text = word.enExample
            binding.tvTrExample.text = word.trExample
            binding.tvLevel.text = word.level
            binding.tvMeaning.text = word.meaning
            binding.favoriteIcon.setOnClickListener {
                onClickBtnRemove?.invoke(word.word)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteWordViewHolder {
        val binding = ItemFavoriteCommonWordsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteWordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteWordViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int = words.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newWords: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(words, newWords)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        words = newWords
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnClickBtnRemoveListener(listener: (word: String) -> Unit) {
        onClickBtnRemove = listener
    }

    class FavoriteDiffCallback(
        private val oldList: List<Favorite>,
        private val newList: List<Favorite>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].word == newList[newItemPosition].word
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

