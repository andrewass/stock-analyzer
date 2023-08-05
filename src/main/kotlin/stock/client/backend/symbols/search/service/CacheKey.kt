package stock.client.backend.symbols.search.service

import stock.client.backend.symbols.search.domain.Period

data class CacheKey(
    val symbol: String,
    val period: Period
)
