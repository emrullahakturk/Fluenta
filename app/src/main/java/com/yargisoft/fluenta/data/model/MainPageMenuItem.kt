package com.yargisoft.fluenta.data.model

import javax.inject.Inject

data class MainPageMenuItem @Inject constructor (
    val destinationId: Int,
    val iconResId: Int // Drawable resource ID
)