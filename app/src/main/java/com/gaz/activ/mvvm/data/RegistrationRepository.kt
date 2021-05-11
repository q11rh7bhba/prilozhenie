package com.gaz.activ.mvvm.data

import com.gaz.activ.mvvm.entity.RegistrationData
import com.gaz.activ.mvvm.entity.User
import io.reactivex.Single

interface RegistrationRepository {

    fun register(user: User, registrationData: RegistrationData): Single<User>
}