package com.gaz.activ.mvvm.data.network.repository

import com.gaz.activ.mvvm.data.RegistrationRepository
import com.gaz.activ.mvvm.data.mapper.UserToUserRequestMapper
import com.gaz.activ.mvvm.data.network.retrofit.ApiService
import com.gaz.activ.mvvm.entity.RegistrationData
import com.gaz.activ.mvvm.entity.User
import io.reactivex.Scheduler
import io.reactivex.Single

class RegistrationRepositoryNetwork(
        private val apiService: ApiService,
        private val computationScheduler: Scheduler,
        private val userMapper: UserToUserRequestMapper
) : RegistrationRepository {

    override fun register(user: User, registrationData: RegistrationData) =
        Single.fromCallable { userMapper.apply(user, registrationData) }
            .flatMap(apiService::register)
            .map { user.copy(url = it.dataResponse?.redirectUrl) }
            .observeOn(computationScheduler)
}