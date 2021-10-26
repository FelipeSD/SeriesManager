package com.seriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Serie(
    val name: String = "",
    val releaseYear: Int = 0,
    val producer: String = "",
    val genre: String = ""
): Parcelable
