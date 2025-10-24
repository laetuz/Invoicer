package id.neotica.invoicer

import android.app.Application
import id.neotica.invoicer.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        initializeKoin { androidContext(this@MyApp) }
    }
}