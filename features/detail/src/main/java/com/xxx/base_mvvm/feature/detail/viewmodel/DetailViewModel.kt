package com.xxx.base_mvvm.feature.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxx.base_mvvm.core.common.result.Result
import com.xxx.base_mvvm.core.domain.usecase.user.GetUserByIdUseCase
import com.xxx.base_mvvm.feature.detail.navigation.DETAIL_USER_ID_ARG
import com.xxx.base_mvvm.feature.detail.state.DetailUiEvent
import com.xxx.base_mvvm.feature.detail.state.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId: Int = checkNotNull(savedStateHandle[DETAIL_USER_ID_ARG])

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<DetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init { loadUser() }

    fun onBack() { viewModelScope.launch { _uiEvent.send(DetailUiEvent.NavigateBack) } }

    private fun loadUser() {
        getUserByIdUseCase(userId).onEach { result ->
            _uiState.update {
                when (result) {
                    is Result.Loading -> it.copy(isLoading = true)
                    is Result.Success -> it.copy(isLoading = false, user = result.data)
                    is Result.Error   -> it.copy(isLoading = false, error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}
