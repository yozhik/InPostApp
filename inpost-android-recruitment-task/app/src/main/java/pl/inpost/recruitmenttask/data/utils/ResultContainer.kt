package pl.inpost.recruitmenttask.data.utils

sealed class ResultContainer<out T> {
    data class Success<T>(val data: T) : ResultContainer<T>()
    data class Error(val message: String, val cause: Exception? = null) : ResultContainer<Nothing>()
}

val <T> ResultContainer<T>.isSuccess: Boolean
    get() = this is ResultContainer.Success<T>

val <T> ResultContainer<T>.isFailure: Boolean
    get() = this is ResultContainer.Error

fun ResultContainer<*>.getErrorMessage(): String {
    return when (this) {
        is ResultContainer.Error -> message
        else -> ""
    }
}

fun <T> ResultContainer<T>.getSuccessDataOrNull(): T? {
    return when (this) {
        is ResultContainer.Success -> data
        else -> null
    }
}