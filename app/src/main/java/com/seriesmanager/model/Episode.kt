package com.seriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val sequence: Int = 0,
    val name: String = "",
    val duration: Number = 0,
    val watched: Boolean = false
): Parcelable
