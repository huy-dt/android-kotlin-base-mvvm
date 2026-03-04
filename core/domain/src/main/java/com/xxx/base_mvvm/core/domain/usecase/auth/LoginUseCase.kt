package com.xxx.base_mvvm.core.domain.usecase.auth

import com.xxx.base_mvvm.core.common.dispatcher.AppDispatchers
import com.xxx.base_mvvm.core.common.dispatcher.Dispatcher
import com.xxx.base_mvvm.core.domain.model.AuthToken
import com.xxx.base_mvvm.core.domain.repository.AuthRepository
import com.xxx.base_mvvm.core.domain.usecase.base.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class LoginParams(val email: String, val password: String)

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(AppDispatchers.IO) dispatcher: CoroutineDispatcher
) : SuspendUseCase<LoginParams, AuthToken>(dispatcher) {

    override suspend fun execute(params: LoginParams): AuthToken =
        authRepository.login(params.email, params.password)
}
