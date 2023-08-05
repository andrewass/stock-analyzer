package stock.client.backend.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import stock.client.backend.symbols.populate.consumer.FinnHubConsumer
import stock.client.backend.symbols.populate.consumer.StockConsumer
import stock.client.backend.symbols.populate.service.DefaultSymbolPopulatorService
import stock.client.backend.symbols.populate.service.SymbolPopulatorService
import stock.client.backend.symbols.search.consumer.FastFinanceConsumer
import stock.client.backend.symbols.search.consumer.SymbolConsumer
import stock.client.backend.symbols.search.route.DefaultSymbolSearchProvider
import stock.client.backend.symbols.search.route.SymbolSearchProvider
import stock.client.backend.symbols.search.service.DefaultSymbolSearchService
import stock.client.backend.symbols.search.service.SymbolSearchService
import stock.client.backend.symbols.trending.service.DefaultTrendingSymbolsService
import stock.client.backend.symbols.trending.service.TrendingSymbolsService

/**
 * Make components available with dependency injection using Kodein
 */
val kodein = DI {

    bindSingleton { getCacheManager() }

    bindSingleton { getRedisClient() }

    bindSingleton<StockConsumer> { FinnHubConsumer() }

    bindSingleton<SymbolPopulatorService> { DefaultSymbolPopulatorService(instance(), instance()) }

    bindSingleton<TrendingSymbolsService> { DefaultTrendingSymbolsService(instance()) }

    bindSingleton<SymbolConsumer> { FastFinanceConsumer(getHttpClient(), "http://fastfinance-service:8000") }

    bindSingleton<SymbolSearchService> { DefaultSymbolSearchService(instance(), instance(), instance()) }

    bindSingleton<SymbolSearchProvider> { DefaultSymbolSearchProvider(instance()) }

}

@OptIn(ExperimentalSerializationApi::class)
fun getHttpClient(): HttpClient =
    HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }
        install(Logging) {
            logger = Logger.DEFAULT
        }
    }