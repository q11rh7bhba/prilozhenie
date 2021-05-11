package com.gaz.activ.mvvm.data.network.entity

import com.google.gson.annotations.SerializedName

data class DataResponse(
        @SerializedName("proceedWith")
        val proceedWith: String?,

        @SerializedName("redirectUrl")
        val redirectUrl: String?
)