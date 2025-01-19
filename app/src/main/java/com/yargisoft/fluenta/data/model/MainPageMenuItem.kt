package com.yargisoft.fluenta.data.model

import javax.inject.Inject

data class MainPageMenuItem @Inject constructor (
    val destination: String,
    val iconResId: Int // Drawable resource ID
)