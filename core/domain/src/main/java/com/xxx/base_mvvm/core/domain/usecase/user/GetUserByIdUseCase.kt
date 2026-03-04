package com.xxx.base_mvvm.core.domain.usecase.user

import com.xxx.base_mvvm.core.common.dispatcher.AppDispatchers
import com.xxx.base_mvvm.core.common.dispatcher.Dispatcher
import com.xxx.base_mvvm.core.domain.model.User
import com.xxx.base_mvvm.core.domain.repository.UserRepository
import com.xxx.base_mvvm.core.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @Dispatcher(AppDispatchers.IO) dispatcher: CoroutineDispatcher
) : FlowUseCase<Int, User?>(dispatcher) {

    override fun execute(params: Int): Flow<User?> =
        userRepository.getUserById(params)
}
