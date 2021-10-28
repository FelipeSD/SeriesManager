package com.seriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Season(
    val sequence: String = "",
    val releaseYear: String = "",
    val numberEpisodes: String = ""
): Parcelable
