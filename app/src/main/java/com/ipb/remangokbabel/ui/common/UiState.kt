package com.ipb.remangokbabel.ui.common

sealed class UiState<out T: Any?> {
    object Loading : UiState<Nothing>()
    object Idle : UiState<Nothing>()
    data class Success<out T: Any>(val data: T) : UiState<T>()
    data class Error<out U: Any>(val errorData: U) : UiState<Nothing>()
}