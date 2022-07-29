package com.task.bosta.utils

sealed class LiveDataResource<T>(
        val data: T? = null,
        val message: String? = null
) {
    class Success<T>(data: T) : LiveDataResource<T>(data)
    class ErrorResponse<T>(data: T? = null) : LiveDataResource<T>(data)
    class Loading<T> : LiveDataResource<T>()
    class NoNetwork<T> : LiveDataResource<T>()
    class Exception<T> : LiveDataResource<T>()
    class EmptyState<T> : LiveDataResource<T>()
}