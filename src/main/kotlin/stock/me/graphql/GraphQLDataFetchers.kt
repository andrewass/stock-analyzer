package stock.me.graphql

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.ktor.features.*
import stock.me.provider.response.StockQuoteDto
import stock.me.provider.toStockQuoteDto
import yahoofinance.YahooFinance


class GraphQLDataFetchers {

    fun stockQuoteDataFetcher(): DataFetcher<StockQuoteDto> {
        return DataFetcher { environment: DataFetchingEnvironment ->

            environment.getArgument<String>("symbol")
                ?.let { YahooFinance.get(it) }
                ?.let { toStockQuoteDto(it) }
                ?: throw NotFoundException("Stock Quote : No results found for symbol")
        }
    }
}