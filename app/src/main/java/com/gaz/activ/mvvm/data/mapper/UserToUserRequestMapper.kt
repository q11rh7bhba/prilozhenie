package com.gaz.activ.mvvm.data.mapper

import com.gaz.activ.mvvm.data.network.entity.UserRequest
import com.gaz.activ.mvvm.entity.RegistrationData
import com.gaz.activ.mvvm.entity.User
import io.reactivex.functions.BiFunction

class UserToUserRequestMapper : BiFunction<User, RegistrationData, UserRequest> {

    override fun apply(user: User, registrationData: RegistrationData) =
        UserRequest(
            name = user.name,
            surname = user.surname,
            phone = user.phone,
            email = user.email,
            country = user.country,
            site = registrationData.site ?: SITE_DEFAULT
        )

    companion object {

        private const val SITE_DEFAULT = "gazactiv"
    }
}