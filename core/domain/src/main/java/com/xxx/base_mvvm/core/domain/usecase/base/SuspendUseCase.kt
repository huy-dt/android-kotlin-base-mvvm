package com.xxx.base_mvvm.core.domain.usecase.base

import com.xxx.base_mvvm.core.common.dispatcher.AppDispatchers
import com.xxx.base_mvvm.core.common.dispatcher.Dispatcher
import com.xxx.base_mvvm.core.common.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<in P, out R>(
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(params: P): Result<R> = withContext(dispatcher) {
        runCatching { execute(params) }
            .fold(
                onSuccess = { Result.Success(it) },
                onFailure = { Result.Error(it) }
            )
    }

    protected abstract suspend fun execute(params: P): R
}
