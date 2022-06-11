package stock.me.symbols.domain

enum class Currency(val forexCode : String) {
    USD("USD"),
    NOK("USDNOK=X"),
    EUR("USDEUR=X"),
    GBP("USDGBP=X")
}