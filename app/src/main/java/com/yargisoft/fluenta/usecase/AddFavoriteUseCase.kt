package com.yargisoft.fluenta.usecase

import android.widget.ImageButton
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.viewmodel.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor() {

    fun addFavorite(
        viewModel: FavoriteViewModel,
        category: String,
        word: String,
        type: String,
        level: String,
        meaning: String,
        trExample: String,
        enExample: String,
        imageButton: ImageButton
    ) {

        CoroutineScope(Dispatchers.Main).launch {
            if (viewModel.isWordFavorite(
                    word,
                    category
                )
            ) {

                viewModel.removeFavorite(
                    word,
                    category
                )
                imageButton.setImageResource(R.drawable.ic_favorite)
            } else {

                viewModel.addFavorite(
                    word,
                    category,
                    level,
                    meaning,
                    trExample,
                    enExample
                )
                imageButton.setImageResource(R.drawable.ic_favorite_filled)
            }
        }
    }
}