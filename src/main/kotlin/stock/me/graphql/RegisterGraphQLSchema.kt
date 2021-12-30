package stock.me.graphql

import com.apurebase.kgraphql.GraphQL
import io.ktor.application.*
import kotlinx.datetime.LocalDate
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.provider.ServiceProvider
import stock.me.provider.response.HistoricalQuoteDto
import stock.me.provider.response.StockQuoteDto


fun Application.registerGraphQLSchema() {

    val serviceProvider by kodein.instance<ServiceProvider>()

    install(GraphQL) {
        playground = true

        schema {
            query("stockQuote") {
                description = "Returns a stock quote for a given symbol"

                resolver { symbol: String ->
                    serviceProvider.getStockQuote(symbol)
                }
            }

            query("stockQuotesTrending") {
                description = "Returns a list of stock quotes for trending symbols"

                resolver { ->
                    serviceProvider.getStockQuotesOfTrendingSymbols()
                }
            }

            query("stockQuotesHistorical"){
                description = "Returns a list of historical stock quotes for a given symbol"

                resolver { symbol: String ->
                    serviceProvider.getHistoricalQuotes(symbol)
                }
            }

            stringScalar<LocalDate> {
                serialize = { date -> date.toString() }
                deserialize = { dateString -> LocalDate.parse(dateString)}
            }

            type<StockQuoteDto> {
                description = "A stock quote containing price information for a given symbol"
            }

            type<HistoricalQuoteDto> {
                description = "A list of historical stock quotes for a given symbol"
            }
        }
    }
}