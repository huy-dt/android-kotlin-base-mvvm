package com.xxx.base_mvvm.core.domain.usecase.auth

import com.xxx.base_mvvm.core.common.dispatcher.AppDispatchers
import com.xxx.base_mvvm.core.common.dispatcher.Dispatcher
import com.xxx.base_mvvm.core.domain.repository.AuthRepository
import com.xxx.base_mvvm.core.domain.usecase.base.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(AppDispatchers.IO) dispatcher: CoroutineDispatcher
) : SuspendUseCase<Unit, Unit>(dispatcher) {
    override suspend fun execute(params: Unit) = authRepository.logout()
}
