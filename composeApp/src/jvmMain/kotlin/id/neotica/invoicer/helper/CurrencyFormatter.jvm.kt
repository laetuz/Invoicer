package id.neotica.invoicer.helper

import java.text.NumberFormat
import java.util.Locale

actual fun Long.toIdr(): String {
    val localeID = Locale.Builder()
        .setRegion("ID")
        .build()
    val formatter = NumberFormat.getCurrencyInstance(localeID)

    formatter.maximumFractionDigits = 0

    return formatter.format(this).replace("Rp", "Rp ")
}

actual fun Long.toUsd(): String {
    val localeID = Locale.Builder()
        .setRegion("US")
        .build()
    val formatter = NumberFormat.getCurrencyInstance(localeID)

    formatter.maximumFractionDigits = 0

    return formatter.format(this).replace("$", "$ ")
}