package com.gaz.activ.mvvm.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize data class User(
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val country: String,
    val url: String?
): Parcelable