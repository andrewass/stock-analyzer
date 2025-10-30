package stockcomp.client.backend.symbols.search.domain

enum class Period(
    val decode: String,
) {
    MONTH1("1mo"),
    MONTH6("6mo"),
    THIS_YEAR("ytd"),
    YEAR1("1y"),
    YEAR5("5y"),
    YEAR10("10y"),
    MAX("max"),
}
