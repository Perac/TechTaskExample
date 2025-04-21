package hr.nullsafe.ernietechtask

import kotlinx.coroutines.Dispatchers

class AppDispatchers: DispatcherSettings {

    override fun mainDispatcher() = Dispatchers.Main

    override fun ioDispatcher() = Dispatchers.IO
}