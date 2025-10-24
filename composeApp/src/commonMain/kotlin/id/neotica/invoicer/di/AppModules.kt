package id.neotica.invoicer.di

import id.neotica.invoicer.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initializeKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)

    modules(platformModule())
}

//interface fun