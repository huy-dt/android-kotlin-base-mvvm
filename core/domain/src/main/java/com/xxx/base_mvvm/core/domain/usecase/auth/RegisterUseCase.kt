package com.xxx.base_mvvm.core.domain.usecase.auth

import com.xxx.base_mvvm.core.common.dispatcher.AppDispatchers
import com.xxx.base_mvvm.core.common.dispatcher.Dispatcher
import com.xxx.base_mvvm.core.domain.model.User
import com.xxx.base_mvvm.core.domain.repository.AuthRepository
import com.xxx.base_mvvm.core.domain.usecase.base.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class RegisterParams(
    val name: String,
    val email: String,
    val password: String
)

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(AppDispatchers.IO) dispatcher: CoroutineDispatcher
) : SuspendUseCase<RegisterParams, User>(dispatcher) {
    override suspend fun execute(params: RegisterParams): User =
        authRepository.register(params.name, params.email, params.password)
}
