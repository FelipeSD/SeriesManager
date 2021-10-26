package com.seriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Season(
    val sequence: Int = 0,
    val releaseYear: Int = 0,
    val numberEpisodes: Int = 0
): Parcelable
