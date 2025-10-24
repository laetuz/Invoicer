package id.neotica.invoicer

import org.koin.core.module.Module

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()
actual fun platformModule(): Module {
    TODO("Not yet implemented")
}