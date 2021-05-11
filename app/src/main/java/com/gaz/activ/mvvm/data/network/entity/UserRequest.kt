package com.gaz.activ.mvvm.data.network.entity

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("first_name")
    val name: String,

    @SerializedName("last_name")
    val surname: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("site")
    val site: String,

    @SerializedName("landing")
    val landing: String = "5eb177835a0071baef6ea3b8"
)