package tech.rayyaw.sprintlux

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tech.rayyaw.sprintlux.input.InputProcessor
import tech.rayyaw.sprintlux.input.DevInputProcessor

class AppModule: AbstractModule() {
    override fun configure() {
        bind(InputProcessor::class.java).to(DevInputProcessor::class.java).`in`(Scopes.SINGLETON)
    }

    @Provides
    @Singleton
    fun providesLogger(): Logger {
        return LoggerFactory.getLogger(AppModule::class.java)
    }

    @Provides
    @Singleton
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Default)
    }
}