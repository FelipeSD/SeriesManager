package com.seriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val sequence: String = "",
    val name: String = "",
    val duration: String = "",
    val watched: Boolean = false
): Parcelable
