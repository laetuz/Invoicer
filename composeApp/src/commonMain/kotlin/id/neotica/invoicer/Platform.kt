package id.neotica.invoicer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform