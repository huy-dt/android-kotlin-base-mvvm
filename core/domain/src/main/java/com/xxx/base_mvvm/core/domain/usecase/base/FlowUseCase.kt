package com.xxx.base_mvvm.core.domain.usecase.base

import com.xxx.base_mvvm.core.common.dispatcher.AppDispatchers
import com.xxx.base_mvvm.core.common.dispatcher.Dispatcher
import com.xxx.base_mvvm.core.common.result.Result
import com.xxx.base_mvvm.core.common.util.asResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

abstract class FlowUseCase<in P, out R>(
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(params: P): Flow<Result<R>> =
        execute(params).asResult().flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<R>
}

abstract class NoParamFlowUseCase<out R>(
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<Result<R>> =
        execute().asResult().flowOn(dispatcher)

    protected abstract fun execute(): Flow<R>
}
