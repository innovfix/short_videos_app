package com.app.reelshort.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategorySection(
    val title: String,
    val icon: Int,
    val items: List<CommonInfo>,
) : Parcelable