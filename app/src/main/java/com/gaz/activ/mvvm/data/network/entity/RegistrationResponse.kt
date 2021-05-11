package com.gaz.activ.mvvm.data.network.entity

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
        @SerializedName("status")
        val status: Boolean?,

        @SerializedName("data")
        val dataResponse: DataResponse?
)