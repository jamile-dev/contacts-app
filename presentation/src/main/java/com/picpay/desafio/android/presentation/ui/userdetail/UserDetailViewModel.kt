package com.picpay.desafio.android.presentation.ui.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.androd.domain.model.Result
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.androd.domain.usecase.GetUserByIdUseCase
import com.picpay.desafio.android.presentation.ui.state.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase,
) : ViewModel() {
    private val _userState = MutableStateFlow<UIState<User>>(UIState.Loading)
    val userState: StateFlow<UIState<User>> = _userState

    fun loadUserById(id: Int) {
        viewModelScope.launch {
            _userState.value = UIState.Loading
            val result = getUserByIdUseCase(id)
            _userState.value =
                when (result) {
                    is Result.Success -> UIState.Success(result.data)
                    is Result.Error -> UIState.Error(result.exception)
                }
        }
    }
}
