package data

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Success<T>(val value: T) : Resource<T>()
    class Failed<T>(val error: Throwable) : Resource<T>()
    class Empty<T> : Resource<T>()

    fun isLoading(): Boolean {
        return (this is Loading)
    }

    fun isSuccess(): Boolean {
        return (this is Success)
    }

    fun isFailed(): Boolean {
        return (this is Failed)
    }

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(value: T) = Success(value)
        fun <T> failed(error: Throwable) = Failed<T>(error)
    }
}
