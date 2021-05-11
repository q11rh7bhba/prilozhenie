package com.gaz.activ.mvvm.data.network.retrofit

import com.gaz.activ.mvvm.data.network.entity.RegistrationResponse
import com.gaz.activ.mvvm.data.network.entity.UserRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST( "api/leads/google")
    fun register(@Body userRequest: UserRequest): Single<RegistrationResponse>
}