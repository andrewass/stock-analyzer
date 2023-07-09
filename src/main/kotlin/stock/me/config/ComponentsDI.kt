package stock.me.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import stock.me.symbols.populate.consumer.FinnHubConsumer
import stock.me.symbols.populate.consumer.StockConsumer
import stock.me.symbols.populate.service.DefaultSymbolPopulatorService
import stock.me.symbols.populate.service.SymbolPopulatorService
import stock.me.symbols.search.consumer.FastFinanceConsumer
import stock.me.symbols.search.consumer.SymbolConsumer
import stock.me.symbols.search.route.DefaultSymbolSearchProvider
import stock.me.symbols.search.route.SymbolSearchProvider
import stock.me.symbols.search.service.DefaultSymbolSearchService
import stock.me.symbols.search.service.SymbolSearchService
import stock.me.symbols.trending.service.DefaultTrendingSymbolsService
import stock.me.symbols.trending.service.TrendingSymbolsService

/**
 * Make components available with dependency injection using Kodein
 */
val kodein = DI {

    bindSingleton { getCacheManager() }

    bindSingleton { getRedisClient() }

    bindSingleton<StockConsumer> { FinnHubConsumer() }

    bindSingleton<SymbolPopulatorService> { DefaultSymbolPopulatorService(instance(), instance()) }

    bindSingleton<TrendingSymbolsService> { DefaultTrendingSymbolsService(instance()) }

    bindSingleton<SymbolConsumer> { FastFinanceConsumer(getHttpClient(), "") }

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
    }