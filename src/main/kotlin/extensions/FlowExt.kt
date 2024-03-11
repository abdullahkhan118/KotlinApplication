package extensions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private val <T> MutableStateFlow<T>.mutex: Mutex
    get() = Mutex()

suspend fun <T> MutableStateFlow<T>.updateWithLock(function: (T) -> T) = mutex.withLock {
    update(function)
}