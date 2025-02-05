package com.yargisoft.fluenta.views.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.data.model.Favorite
import com.yargisoft.fluenta.databinding.ItemPhrasesBinding
import com.yargisoft.fluenta.usecase.TextToSpeechUseCase
import javax.inject.Inject

class PhrasesFavoritesAdapter @Inject constructor() :
    RecyclerView.Adapter<PhrasesFavoritesAdapter.FavoritePhraseViewHolder>() {

    @Inject
    lateinit var textToSpeechUseCase: TextToSpeechUseCase

    private var phrases: List<Favorite> = emptyList()
    private var onClickBtnRemove: ((phrase: String) -> Unit)? = null

    inner class FavoritePhraseViewHolder(private val binding: ItemPhrasesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(phrase: Favorite) {
            binding.apply {
                tvPhrase.text = phrase.word
                tvEnExample.text = phrase.enExample
                tvTrExample.text = phrase.trExample
                tvLevel.text = phrase.level
                tvMeaning.text = phrase.meaning
                favoriteIcon.setImageResource(R.drawable.ic_favorite_filled)
                favoriteIcon.setOnClickListener {
                    onClickBtnRemove?.invoke(phrase.word)
                }
                btnSpeak.setOnClickListener {
                    val wordToSpeech = "${tvPhrase.text}  '.'   ${tvEnExample.text} "
                    textToSpeechUseCase.speak(wordToSpeech)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePhraseViewHolder {
        val binding =
            ItemPhrasesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritePhraseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritePhraseViewHolder, position: Int) {
        holder.bind(phrases[position])
    }

    override fun getItemCount(): Int = phrases.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newPhrases: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(phrases, newPhrases)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        phrases = newPhrases
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnClickBtnRemoveListener(listener: (phrase: String) -> Unit) {
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

    fun ttsStop(){
        textToSpeechUseCase.stop()
    }
}
