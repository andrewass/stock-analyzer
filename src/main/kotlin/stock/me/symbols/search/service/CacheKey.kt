package stock.me.symbols.search.service

import stock.me.symbols.search.domain.Period

data class CacheKey(
    val symbol: String,
    val period: Period
)
