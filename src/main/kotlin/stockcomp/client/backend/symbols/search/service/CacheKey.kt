package stockcomp.client.backend.symbols.search.service

import stockcomp.client.backend.symbols.search.domain.Period

data class CacheKey(
    val symbol: String,
    val period: Period,
)
