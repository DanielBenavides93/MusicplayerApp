package com.dbtech.musicplayerapp.presentation.ui

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()          // estado inicial, sin acción
    object Loading : UiState<Nothing>()       // cargando
    data class Success<T>(val data: T) : UiState<T>() // éxito con datos
    data class Error(val message: String) : UiState<Nothing>() // error
}