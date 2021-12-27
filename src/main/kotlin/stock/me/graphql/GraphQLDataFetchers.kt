package stock.me.graphql

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.ktor.features.*
import stock.me.model.Currency
import stock.me.service.mapper.toStockQuoteDto
import stock.me.provider.response.StockQuoteDto
import yahoofinance.Stock
import yahoofinance.YahooFinance


class GraphQLDataFetchers {

    fun stockQuoteDataFetcher(): DataFetcher<StockQuoteDto> {
        return DataFetcher { environment: DataFetchingEnvironment ->

            environment.getArgument<String>("symbol")
                ?.let { YahooFinance.get(it) }
                ?.let { mapToStockQuote(it) }
                ?: throw NotFoundException("Stock Quote : No results found for symbol")
        }
    }

    private fun mapToStockQuote(stock: Stock): StockQuoteDto {
        val currency = Currency.valueOf(stock.currency)
        val usdPrice = getUsdPrice(stock.quote.price.toDouble(), currency)

        return toStockQuoteDto(stock, currency, usdPrice)
    }

    private fun getUsdPrice(price: Double, currency: Currency): Double {
        return if (currency == Currency.USD) {
            price
        } else {
            val fxQuote = YahooFinance.getFx(currency.forexCode).price.toDouble()
            price / fxQuote
        }
    }
}