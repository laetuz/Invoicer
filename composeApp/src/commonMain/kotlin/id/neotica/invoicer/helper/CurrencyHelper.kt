package id.neotica.invoicer.helper

fun Long.currencyType(type: CURRENCY): String {
    return when (type) {
        CURRENCY.IDR -> this.toIdr()
        CURRENCY.USD -> this.toUsd()
    }
}

sealed class CURRENCY {
    object IDR : CURRENCY()
    object USD : CURRENCY()
}