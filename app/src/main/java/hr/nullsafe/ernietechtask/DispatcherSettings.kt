package hr.nullsafe.ernietechtask

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Dispatcher settings, useful for testing.
 */
interface DispatcherSettings {

    fun mainDispatcher(): CoroutineDispatcher

    fun ioDispatcher(): CoroutineDispatcher
}