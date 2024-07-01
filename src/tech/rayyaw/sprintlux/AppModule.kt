package tech.rayyaw.sprintlux

import com.google.gson.Gson
import com.google.inject.AbstractModule
import com.google.inject.name.Named
import com.google.inject.Provides
import com.google.inject.Scopes
import java.time.Instant
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tech.rayyaw.sprintlux.input.InputProcessor
import tech.rayyaw.sprintlux.input.DevInputProcessor


// Top level Guice module for the project.
// Handles all dependency injection.
class AppModule: AbstractModule() {
    override fun configure() {
        bind(InputProcessor::class.java).to(DevInputProcessor::class.java).`in`(Scopes.SINGLETON)
    }

    @Provides
    @Singleton
    @Named("startTime")
    // Update this value to manipulate the timer.
    // Setting this to null will cause the timer to freeze at 0.
    fun providesStartTime(): MutableStateFlow<Instant?> {
        return MutableStateFlow(null)
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

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }
}